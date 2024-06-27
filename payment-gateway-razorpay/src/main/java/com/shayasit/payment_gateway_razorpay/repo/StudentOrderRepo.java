package com.shayasit.payment_gateway_razorpay.repo;

import com.shayasit.payment_gateway_razorpay.dto.StudentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentOrderRepo extends JpaRepository<StudentOrder, Integer> {

    public StudentOrder findByRazorpayOrderId(String orderId);
}
