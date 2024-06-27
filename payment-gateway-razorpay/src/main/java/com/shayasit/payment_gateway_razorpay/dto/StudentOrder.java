package com.shayasit.payment_gateway_razorpay.dto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "student_orders")
@Data
@ToString
public class StudentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private String name;
    private String email;
    private String phno;
    private String course;
    private Integer amount;
    private String orderStatus;
    private String razorpayOrderId;
}
