package com.example.camelrabbitmqexample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class PublishRoute extends RouteBuilder {

    @Value("${status.queue}")
    private String destination;
    @Override
    public void configure() throws Exception {

      from("direct:writeQueue")
              .marshal()
              .json(JsonLibrary.Jackson).process (new Processor() {
                  @Override
                  public void process(Exchange exchange) throws Exception {
                      System.out.println("Message Processed!");


                  }
              })
              .toD(destination);

    }
}
