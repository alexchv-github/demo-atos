package com.service.transaction.domain.repository;

import com.service.transaction.domain.model.Channel;

public interface ChannelRepository {

    Channel findByChannel(String channel);

}
