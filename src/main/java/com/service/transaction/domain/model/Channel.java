package com.service.transaction.domain.model;

import javax.validation.constraints.NotBlank;

public class Channel {

    @NotBlank
    private String channel;

    private Boolean subtract;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Boolean getSubtract() {
        return subtract;
    }

    public void setSubtract(Boolean subtract) {
        this.subtract = subtract;
    }
}
