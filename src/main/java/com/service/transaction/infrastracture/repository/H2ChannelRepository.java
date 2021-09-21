package com.service.transaction.infrastracture.repository;

import com.service.transaction.domain.model.Channel;
import com.service.transaction.domain.repository.ChannelRepository;
import com.service.transaction.infrastracture.repository.mapper.ChannelMapper;
import org.springframework.stereotype.Component;

@Component
public class H2ChannelRepository implements ChannelRepository {

    private H2ChannelRepositoryCrud repositoryCrud;
    private ChannelMapper mapper;

    public H2ChannelRepository(H2ChannelRepositoryCrud repositoryCrud, ChannelMapper mapper) {
        this.repositoryCrud = repositoryCrud;
        this.mapper = mapper;
    }

    @Override
    public Channel findByChannel(String channel) {
        return mapper.dbChannelToChannel(repositoryCrud.getByChannel(channel));
    }
}
