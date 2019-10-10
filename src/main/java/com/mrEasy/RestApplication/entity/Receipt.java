package com.mrEasy.RestApplication.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long receiptId;
    @ManyToOne
    @JoinColumn(name = "serviceId")
    private Service service;
    private Double value;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User payer;
    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private Apartment apartment;
    private LocalDateTime formationDate;
    private LocalDateTime paymentDate;

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public LocalDateTime getFormationDate() {
        return formationDate;
    }

    public void setFormationDate(LocalDateTime formationDate) {
        this.formationDate = formationDate;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}