package com.mrEasy.RestApplication.entity;

import javax.persistence.*;

@Entity
@Table
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long apartmentId;
    @ManyToOne
    @JoinColumn(name = "houseId")
    private House house;
    private Integer apartmentNumber;

    public Apartment() {
    }

    public Apartment(House house, Integer apartmentNumber) {
        this.house = house;
        this.apartmentNumber = apartmentNumber;
    }

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}