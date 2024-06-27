package com.shayasit.payment_gateway_razorpay.controller;

import com.razorpay.RazorpayException;
import com.shayasit.payment_gateway_razorpay.dto.StudentOrder;
import com.shayasit.payment_gateway_razorpay.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/")
    public String init() {
        return "index";
    }

    @PostMapping(value = "/create-order", produces = "application/json")
    @ResponseBody
    public ResponseEntity<StudentOrder> createOrder(@RequestBody StudentOrder studentOrder) throws RazorpayException {
        StudentOrder createdOrder = studentService.createOrder(studentOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PostMapping("/handle-payment-callback")
    public String handlePaymentCallback(@RequestParam Map<String, String> responsePayload) {
        log.info("responsePayload: " + responsePayload);
        studentService.updateOrder(responsePayload);
        return "success";
    }
}
