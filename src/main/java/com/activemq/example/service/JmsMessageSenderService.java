package com.activemq.example.service;

import com.activemq.example.domain.JmsMessage;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author bsnyder
 * 
 */
@Service
public class JmsMessageSenderService {

    @Autowired
    private JmsTemplate jmsTemplate;
    
    public void sendMessage(final JmsMessage bean) throws JMSException {

        if (bean.isPersistent()) {
            jmsTemplate.setDeliveryPersistent(bean.isPersistent());
        }

        if (0 != bean.getTimeToLive()) {
            jmsTemplate.setTimeToLive(bean.getTimeToLive());
        }

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(bean.getMessagePayload());
                
                if (bean.getReplyTo() != null && !bean.getReplyTo().equals("")) {
                    ActiveMQQueue replyToQueue = new ActiveMQQueue(bean.getReplyTo());
                    message.setJMSReplyTo(replyToQueue);
                }
                
                return message;
            }
        });
    }
}
