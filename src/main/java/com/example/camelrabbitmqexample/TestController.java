package com.example.camelrabbitmqexample;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.util.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        Map<String,Object> headers = new HashMap<>();
        headers.put("queueName","status.direct");
        headers.put("routingKey","status");
        producerTemplate.sendBodyAndHeaders("direct:writeQueue", "hi",headers);
        return"Success!!";

}


    @GetMapping("break")
    public String breakIt() {
        System.out.println("Hi");
        String returnVal = "";
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("test", "pass");
        try {

            for (int messages = 0; messages < 1000000; messages++) {
                ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
                rabbitConnectionFactory.setUsername("guest");
                rabbitConnectionFactory.setPassword("guest");
                rabbitConnectionFactory.setConnectionTimeout(5000);
                rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
                Connection connection = rabbitConnectionFactory.newConnection();
                Channel channel = connection.createChannel();
                channel.basicPublish("", "status.direct", null, jsonObject.toJson().getBytes());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Success";
    }

    @GetMapping("fill")
    public String fillIt() throws Exception {
        System.out.println("Hi");
        String returnVal = "";
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("test", "pass");
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
        rabbitConnectionFactory.setUsername("guest");
        rabbitConnectionFactory.setPassword("guest");
        rabbitConnectionFactory.setConnectionTimeout(5000);
        ///rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        Connection connection = rabbitConnectionFactory.newConnection();
        Channel channel = connection.createChannel();
        try {

            for (int messages = 0; messages < 10000000; messages++) {
                Thread.sleep(1000);

                channel.basicPublish("", "test.direct", null, jsonObject.toJson().getBytes());

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connection.close();
            channel.close();
        }

        return returnVal;
    }

}
