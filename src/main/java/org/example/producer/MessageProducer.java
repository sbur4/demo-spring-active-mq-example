package org.example.producer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.util.UUID;

@EnableJms
@Slf4j
@RequiredArgsConstructor
@Component
public class MessageProducer {
        @Qualifier("jmsTemplate")
//    @Autowired
    private final JmsTemplate jmsTemplate;

    public void sendMessage(String message) {
        String messageId = UUID.randomUUID().toString();

        try {
            log.info("Sending Message with JMSCorrelationID : {}", messageId);

            jmsTemplate.convertAndSend("QUEUE_TOPIC", message, msgProcessor -> {
                msgProcessor.setJMSCorrelationID(messageId);
                msgProcessor.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10);
                msgProcessor.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 0);
                return msgProcessor;
            });


        } catch (Exception e) {
            log.error("Caught the exception!!! ", e);
            throw new RuntimeException("Cannot send message to the Queue");
        }
    }

    @SneakyThrows
    public String sendAndReceiveMessage(String destination, String messageContent) {
//        return ((TextMessage) jmsTemplate.sendAndReceive(destination, session -> {
//            TextMessage message = session.createTextMessage();
//            message.setText(messageContent);
//            return message;
//        })).getText();

//        TextMessage textMessage = (TextMessage) jmsTemplate.sendAndReceive(destination, session -> {
//            TextMessage message = session.createTextMessage(messageContent);
////            message.setText(messageContent);
//            return message;
//        });
//
//        return textMessage.getText();

        Message received = jmsTemplate.sendAndReceive(destination, session -> {
            ObjectMessage message = session.createObjectMessage(messageContent);
            return message;
        });

        return ((TextMessage) received).getText();
    }
}