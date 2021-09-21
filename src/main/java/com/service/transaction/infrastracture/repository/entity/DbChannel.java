package com.service.transaction.infrastracture.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DbChannel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
