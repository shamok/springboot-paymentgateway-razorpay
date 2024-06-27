package com.shayasit.payment_gateway_razorpay.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.shayasit.payment_gateway_razorpay.dto.StudentOrder;
import com.shayasit.payment_gateway_razorpay.repo.StudentOrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentOrderRepo studentOrderRepo;

    @Value("${razorpay.key.id}")
    private String razorPayKey;

    @Value("${razorpay.secret.key}")
    private String razorPaySecretKey;

    private RazorpayClient razorpayClient;

    public StudentOrder createOrder(StudentOrder studentOrder) throws RazorpayException {

        JSONObject orderReq = new JSONObject();

        orderReq.put("amount", studentOrder.getAmount() * 100); // amount in paisa
        orderReq.put("currency", "INR");
        orderReq.put("receipt", studentOrder.getEmail());

        this.razorpayClient = new RazorpayClient(razorPayKey, razorPaySecretKey);

        // create order in razorpay
        Order rayzorPayOrder = razorpayClient.orders.create(orderReq);
        log.info("rayzorpayorder: " + rayzorPayOrder);

        studentOrder.setRazorpayOrderId(rayzorPayOrder.get("id"));
        studentOrder.setOrderStatus(rayzorPayOrder.get("status"));

        studentOrderRepo.save(studentOrder);

        return studentOrder;
    }

    public StudentOrder updateOrder(Map<String, String> responsePayLoad) {

        String razorPayOrderId = responsePayLoad.get("razorpay_order_id");
        StudentOrder order = studentOrderRepo.findByRazorpayOrderId(razorPayOrderId);
        order.setOrderStatus("PAYMENT_COMPLETED");
        // send email logic

        return studentOrderRepo.save(order);
    }

}
