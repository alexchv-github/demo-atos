package com.service.transaction.infrastracture.configuration;

import java.math.BigDecimal;

import com.service.transaction.infrastracture.repository.H2AccountRepositoryCrud;
import com.service.transaction.infrastracture.repository.H2ChannelRepositoryCrud;
import com.service.transaction.infrastracture.repository.entity.DbAccount;
import com.service.transaction.infrastracture.repository.entity.DbChannel;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private H2ChannelRepositoryCrud h2ChannelRepositoryCrud;
    private H2AccountRepositoryCrud h2AccountRepositoryCrud;

    public DataLoader(H2ChannelRepositoryCrud h2ChannelRepositoryCrud, H2AccountRepositoryCrud h2AccountRepositoryCrud) {
        this.h2ChannelRepositoryCrud = h2ChannelRepositoryCrud;
        this.h2AccountRepositoryCrud = h2AccountRepositoryCrud;
    }

    @Override
    public void run(ApplicationArguments args){
        DbChannel atm = new DbChannel();
        atm.setChannel("ATM");
        atm.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(atm);

        DbChannel client = new DbChannel();
        client.setChannel("CLIENT");
        client.setSubtract(Boolean.TRUE);
        h2ChannelRepositoryCrud.save(client);

        DbChannel internal = new DbChannel();
        internal.setChannel("INTERNAL");
        internal.setSubtract(Boolean.FALSE);
        h2ChannelRepositoryCrud.save(internal);

        DbAccount account = new DbAccount();
        account.setIban("ES9820385778983000760236");
        account.setBalance(new BigDecimal("1450.50"));
        h2AccountRepositoryCrud.save(account);
    }
}
