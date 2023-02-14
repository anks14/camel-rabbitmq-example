package com.example.camelrabbitmqexample;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private ProducerTemplate producerTemplate;


    @GetMapping("push")
    public String pushToDymQueue() throws Exception {
        System.out.println("Write Message to Dym Queue");
        Map<String, Object> headers = new HashMap<>();
        headers.put("queueName", "status.direct");
        headers.put("routingKey", "status");
        producerTemplate.sendBodyAndHeaders("direct:writeQueue", "hi", headers);
        return "Success!!";

    }


}
