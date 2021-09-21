package com.service.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.service.transaction.domain.model.ChannelEnum;
import com.service.transaction.domain.model.StatusEnum;
import com.service.transaction.infrastracture.repository.H2ChannelRepositoryCrud;
import com.service.transaction.infrastracture.repository.H2TransactionRepositoryCrud;
import com.service.transaction.infrastracture.repository.entity.DbChannel;
import com.service.transaction.infrastracture.repository.entity.DbTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application.properties")
public class TransactionTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private H2TransactionRepositoryCrud h2TransactionRepositoryCrud;

    @Autowired
    private H2ChannelRepositoryCrud h2ChannelRepositoryCrud;

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andClient_thenNotFound() throws Exception{
        final String referenceResponse = "XXXXXXX";
        final String statusResponse = "INVALID";

        mvc.perform(get("/transactions/CLIENT/XXXXXXX"))
                .andExpect(jsonPath("$.reference", is(referenceResponse)))
                .andExpect(jsonPath("$.status", is(statusResponse)));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andAtm_thenNotFound() throws Exception{
        final String referenceResponse = "XXXXXXX";
        final String statusResponse = "INVALID";

        mvc.perform(get("/transactions/ATM/XXXXXXX"))
                .andExpect(jsonPath("$.reference", is(referenceResponse)))
                .andExpect(jsonPath("$.status", is(statusResponse)));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andInternal_thenNotFound() throws Exception{
        final String referenceResponse = "XXXXXXX";
        final String statusResponse = "INVALID";

        mvc.perform(get("/transactions/INTERNAL/XXXXXXX"))
                .andExpect(jsonPath("$.reference", is(referenceResponse)))
                .andExpect(jsonPath("$.status", is(statusResponse)));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andClient_thenFound_andFeeSubtracted_andSettled() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDescription("");
        transaction.setDate(LocalDate.now().minusDays(1));
        transaction.setStatus(StatusEnum.SETTLED.name());
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.CLIENT.name());
        channel.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(channel);


        mvc.perform(get("/transactions/CLIENT/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.SETTLED.name())))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().subtract(transaction.getFee()).doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andATM_thenFound_andFeeSubtracted_andSettled() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES000000000");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150.00"));
        transaction.setDate(LocalDate.now().minusDays(1));
        transaction.setDescription("");
        transaction.setStatus(StatusEnum.SETTLED.name());
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.ATM.name());
        channel.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/ATM/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.SETTLED.name())))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().subtract(transaction.getFee()).doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andInternal_thenFound_andSettled() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().minusDays(1));
        transaction.setDescription("");
        transaction.setStatus(StatusEnum.SETTLED.name());
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.INTERNAL.name());
        channel.setSubtract(Boolean.FALSE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/INTERNAL/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.SETTLED.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().doubleValue())))
                .andExpect(jsonPath("$.fee", is(transaction.getFee().doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andSameDay_andClient_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now());
        transaction.setStatus(StatusEnum.PENDING.name());
        transaction.setDescription("");
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.CLIENT.name());
        channel.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/CLIENT/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.PENDING.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().subtract(transaction.getFee()).doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andSameDay_andAtm_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now());
        transaction.setDescription("");
        transaction.setStatus(StatusEnum.PENDING.name());
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.ATM.name());
        channel.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/ATM/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.PENDING.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().subtract(transaction.getFee()).doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andSameDay_andInternal_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now());
        transaction.setDescription("");
        transaction.setStatus(StatusEnum.PENDING.name());
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.INTERNAL.name());
        channel.setSubtract(Boolean.FALSE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/INTERNAL/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.PENDING.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().doubleValue())))
                .andExpect(jsonPath("$.fee", is(transaction.getFee().doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andGreaterDay_andClient_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().plusDays(1));
        transaction.setStatus(StatusEnum.FUTURE.name());
        transaction.setDescription("");
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.CLIENT.name());
        channel.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/CLIENT/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.FUTURE.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().subtract(transaction.getFee()).doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andGreaterDay_andAtm_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().plusDays(1));
        transaction.setStatus(StatusEnum.FUTURE.name());
        transaction.setDescription("");
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.ATM.name());
        channel.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/ATM/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.FUTURE.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().subtract(transaction.getFee()).doubleValue())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andGreaterDay_andInternal_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setIban("ES53153513");
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().plusDays(1));
        transaction.setStatus(StatusEnum.FUTURE.name());
        transaction.setDescription("");
        h2TransactionRepositoryCrud.save(transaction);

        DbChannel channel = new DbChannel();
        channel.setChannel(ChannelEnum.INTERNAL.name());
        channel.setSubtract(Boolean.FALSE);
        h2ChannelRepositoryCrud.save(channel);

        mvc.perform(get("/transactions/INTERNAL/12345"))
                .andExpect(jsonPath("$.status", is(StatusEnum.FUTURE.name())))
                .andExpect(jsonPath("$.reference", is("12345")))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount().doubleValue())))
                .andExpect(jsonPath("$.fee", is(transaction.getFee().doubleValue())));
    }
}
