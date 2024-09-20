package org.example.consumer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@EnableJms
@Slf4j
@RequiredArgsConstructor
@Component
public class MessageConsumer {
    @Qualifier("jmsTemplate")
    @NonNull
//    @Autowired
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "QUEUE_TOPIC")
    public void receiveMessage(String message) {
        try {
            log.info("Received message from activeMQ : {}", message);
        } catch (Exception e) {
            log.error("Unknown Error occurred in processing CustomMessage", e);
            throw new RuntimeException("Problem in receiving message from Active MQ");
        }
    }

/*    @JmsListener(destination = "my-queue")
    public void receiveMessage(Message message, Session session) throws JMSException {
        if (message instanceof TextMessage) {
            String requestText = ((TextMessage) message).getText();
            System.out.println("Received request: " + requestText);

            // Only proceed if replyTo header is present
            Destination replyTo = message.getJMSReplyTo();
            if (replyTo != null) {
                String responseText = "Response to: " + requestText;
                jmsTemplate.convertAndSend(replyTo, responseText, messagePostProcessor -> {
                    messagePostProcessor.setJMSCorrelationID(message.getJMSCorrelationID());
                    return messagePostProcessor;
                });
            }
        }
    }*/

    @JmsListener(destination = "my.queue")
//    @SendTo("my.queue")
    public String receiveMessage2(String message) throws JMSException {
        String textMessage = message;
        // Process the received message
        System.out.println("Received: " + textMessage);
        return textMessage + "1";
    }
}