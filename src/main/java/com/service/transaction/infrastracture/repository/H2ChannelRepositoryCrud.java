package com.service.transaction.infrastracture.repository;

import com.service.transaction.infrastracture.repository.entity.DbChannel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2ChannelRepositoryCrud extends CrudRepository<DbChannel, Long> {

    DbChannel getByChannel(String channel);
}
