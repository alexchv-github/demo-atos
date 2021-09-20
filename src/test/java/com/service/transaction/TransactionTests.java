package com.service.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.service.transaction.domain.model.StatusEnum;
import com.service.transaction.infrastracture.repository.H2TransactionRepositoryCrud;
import com.service.transaction.infrastracture.repository.entity.DbTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "/application.properties")
public class TransactionTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private H2TransactionRepositoryCrud h2TransactionRepositoryCrud;

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andClient_thenNotFound() throws Exception{
        final String referenceResponse = "XXXXXXX";
        final String statusResponse = "INVALID";

        mvc.perform(get("/transaction/CLIENT/XXXXXXX"))
                .andExpect(jsonPath("$[0].reference", is(referenceResponse)))
                .andExpect(jsonPath("$[0].status", is(statusResponse)));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andAtm_thenNotFound() throws Exception{
        final String referenceResponse = "XXXXXXX";
        final String statusResponse = "INVALID";

        mvc.perform(get("/transaction/ATM/XXXXXXX"))
                .andExpect(jsonPath("$[0].reference", is(referenceResponse)))
                .andExpect(jsonPath("$[0].status", is(statusResponse)));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andInternal_thenNotFound() throws Exception{
        final String referenceResponse = "XXXXXXX";
        final String statusResponse = "INVALID";

        mvc.perform(get("/transaction/INTERNAL/XXXXXXX"))
                .andExpect(jsonPath("$[0].reference", is(referenceResponse)))
                .andExpect(jsonPath("$[0].status", is(statusResponse)));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andClient_thenFound_andFeeSubtracted_andSettled() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().minusDays(1));
        transaction.setStatus(StatusEnum.SETTLED.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/CLIENT/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.SETTLED)))
                .andExpect(jsonPath("$[0].Amount", is(transaction.getAmount().subtract(transaction.getFee()))));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andATM_thenFound_andFeeSubtracted_andSettled() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().minusDays(1));
        transaction.setStatus(StatusEnum.SETTLED.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/ATM/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.SETTLED)))
                .andExpect(jsonPath("$[0].Amount", is(transaction.getAmount().subtract(transaction.getFee()))));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andDayBefore_andInternal_thenFound_andSettled() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().minusDays(1));
        transaction.setStatus(StatusEnum.SETTLED.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/ATM/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.SETTLED)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount())))
                .andExpect(jsonPath("$[0].fee", is(transaction.getFee())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andSameDay_andClient_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now());
        transaction.setStatus(StatusEnum.PENDING.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/CLIENT/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.PENDING)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount().subtract(transaction.getFee()))));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andSameDay_andAtm_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now());
        transaction.setStatus(StatusEnum.PENDING.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/ATM/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.PENDING)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount().subtract(transaction.getFee()))));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andSameDay_andInternal_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now());
        transaction.setStatus(StatusEnum.PENDING.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/INTERNAL/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.PENDING)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount())))
                .andExpect(jsonPath("$[0].fee", is(transaction.getAmount())));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andGreaterDay_andClient_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().plusDays(1));
        transaction.setStatus(StatusEnum.FUTURE.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/CLIENT/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.FUTURE)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount().subtract(transaction.getFee()))));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andGreaterDay_andAtm_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().plusDays(1));
        transaction.setStatus(StatusEnum.FUTURE.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/ATM/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.FUTURE)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount().subtract(transaction.getFee()))));
    }

    @Test
    public void givenTransactionInfo_whenGetTransaction_andGreaterDay_andInternal_thenFound_andPending() throws Exception{
        DbTransaction transaction = new DbTransaction();
        transaction.setReference("12345");
        transaction.setFee(new BigDecimal("1.50"));
        transaction.setAmount(new BigDecimal("150"));
        transaction.setDate(LocalDate.now().plusDays(1));
        transaction.setStatus(StatusEnum.FUTURE.name());

        h2TransactionRepositoryCrud.save(transaction);

        mvc.perform(get("/transaction/INTERNAL/12345"))
                .andExpect(jsonPath("$[0].status", is(StatusEnum.FUTURE)))
                .andExpect(jsonPath("$[0].reference", is("12345")))
                .andExpect(jsonPath("$[0].amount", is(transaction.getAmount())))
                .andExpect(jsonPath("$[0].fee", is(transaction.getAmount())));
    }
}
