package com.service.transaction.infrastracture.repository.mapper;

import com.service.transaction.domain.model.Channel;
import com.service.transaction.infrastracture.repository.entity.DbChannel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface ChannelMapper {

    Channel dbChannelToChannel(DbChannel channel);
}
