package com.mrEasy.RestApplication.entity;

import javax.persistence.*;

@Entity
@Table
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long houseId;
    private String address;

    public House() {
    }

    public House(String address) {
        this.address = address;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        return houseId == ((House) obj).getHouseId();
    }
}