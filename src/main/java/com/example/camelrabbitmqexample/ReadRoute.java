package com.example.camelrabbitmqexample;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;


@Component
public class ReadRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

      from("{{read.queue}}")
              .unmarshal()
              .json(JsonLibrary.Jackson).process (new Processor() {
                  @Override
                  public void process(Exchange exchange) throws Exception {

                      System.out.println("Message Processed!");
                  }
              })  .end();
    }
}
