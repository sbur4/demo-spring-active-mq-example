package org.example.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;
import java.util.Collections;

@Configuration
public class ActiveMqConfiguration {
    @Value("${spring.activemq.broker-url}")
    private String activeMQBrokerUrl;

    @Value("${spring.activemq.user}")
    private String activeMQBrokerUsername;

    @Value("${spring.activemq.password}")
    private String activeMQBrokerPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                activeMQBrokerUsername,
                activeMQBrokerPassword,
                activeMQBrokerUrl
        );
        connectionFactory.setTrustedPackages(Collections.singletonList("org.example.controller"));
        return connectionFactory;
    }

    @Bean
//    public JmsOperations jmsTemplate() {
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
//        jmsTemplate.setReceiveTimeout(5000);
        return jmsTemplate;
    }

    @Bean
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory());
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

//    @Bean
//    public MessageConverter messageConverter() {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setTargetType(MessageType.OBJECT);
//        return converter;
//    }

//    @Bean
//    public Queue queueInit() {
//        return new ActiveMQQueue("my-queue");
//    }

//    @Bean
//    public Destination queueInit() {
//        return new ActiveMQQueue("my-queue");
//    }
}