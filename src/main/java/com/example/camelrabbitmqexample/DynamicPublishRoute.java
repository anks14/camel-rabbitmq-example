package com.example.camelrabbitmqexample;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class DynamicPublishRoute extends RouteBuilder {

    @Autowired
    private CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        List<String> routes = new ArrayList<>();
        routes.add("{{dym1.queue}}");
        routes.add("{{dym2.queue}}");

        for (String route : routes) {

            try {
                camelContext.addRoutes(new RouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        from(route)
                                .marshal()
                                .json(JsonLibrary.Jackson).process(new Processor() {
                                    @Override
                                    public void process(Exchange exchange) throws Exception {
                                        System.out.println("Route Registered!");
                                    }
                                });
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();

            }

        }
    }
}