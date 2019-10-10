package com.mrEasy.RestApplication.entity;

import javax.persistence.*;

@Entity
@Table
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serviceId;
    private String title;
    private Double price;
    private boolean forHouse;
    private String unit;

    public boolean isForHouse() {
        return forHouse;
    }

    public void setForHouse(boolean forHouse) {
        this.forHouse = forHouse;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return title;
    }
}
