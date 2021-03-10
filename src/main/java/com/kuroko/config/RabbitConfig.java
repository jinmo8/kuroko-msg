package com.kuroko.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static String DEFAULT_EXCHANGE = "qq.message.rpc";
    public static final String ROUTING_PLAIN_TEXT = "plain";
    public static String ROUTING_IMAGE = "image";

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("qq.message.rpc",false,true);
    }

    @Bean
    public Queue painTextQueue(){
        return new Queue("qq.message.rpc.plaintext",false,false,true);
    }

    @Bean
    public Queue imageQueue(){
        return new Queue("qq.message.rpc.image",false,false,true);
    }

    @Bean
    public Binding bindingPainTextQueue(DirectExchange exchange,Queue painTextQueue){
        return BindingBuilder.bind(painTextQueue)
                .to(exchange)
                .with("plain");
    }

    @Bean
    public Binding bindingImageQueue(DirectExchange exchange,Queue imageQueue){
        return BindingBuilder.bind(imageQueue)
                .to(exchange)
                .with("image");
    }

}
