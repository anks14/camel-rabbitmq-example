package com.example.camelrabbitmqexample;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${userName}")
    private String userName;

    @Value("${password}")
    private String password;

    @Bean("rabbitConnectionFactory")
    public ConnectionFactory rabbitConnectionFactory () {
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
        rabbitConnectionFactory.setUsername(userName);
        rabbitConnectionFactory.setPassword(password);
        rabbitConnectionFactory.setConnectionTimeout(5000);
        rabbitConnectionFactory.setAutomaticRecoveryEnabled(true);
        rabbitConnectionFactory.setNetworkRecoveryInterval(10000);
        return rabbitConnectionFactory;

    }





}
