package com.service.transaction.domain.model;

public class Channel {

    private String channelName;
    private Boolean subtract;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Boolean getSubtract() {
        return subtract;
    }

    public void setSubtract(Boolean subtract) {
        this.subtract = subtract;
    }
}
