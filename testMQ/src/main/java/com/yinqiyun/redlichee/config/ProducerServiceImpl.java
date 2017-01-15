package com.yinqiyun.redlichee.config;

import java.io.Serializable;

import javax.jms.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Component;


//@Component
public class ProducerServiceImpl {

    /*@Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("responseQueue")
    private Destination responseDestination;

    *//**
     * 原生生产消息
     * @throws JMSException
     *//*
    public void sendtest() throws JMSException {
         Connection connection = jmsTemplate.getConnectionFactory().createConnection();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        MessageProducer  messageProducer = session.createProducer(responseDestination);
        //NON_PERSISTENT不持久化。PERSISTENT持久化    jmsTemplate 默认是PERSISTENT
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(new String("abcddd"));
        messageProducer.send(responseDestination,objectMessage);
    }

    public void sendMessage(Destination destination, final String message) {
        System.out.println("---------------生产者发送消息-----------------");
        System.out.println("---------------生产者发了一个消息：" + message);
        jmsTemplate.setSessionTransacted(true);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
				*//*TextMessage textMessage = session.createTextMessage(message);
				textMessage.setJMSReplyTo(responseDestination);
				return textMessage;*//*
*//*                jmsTemplate.setSessionAcknowledgeMode(session.CLIENT_ACKNOWLEDGE);
                session.commit();
                session.rollback();*//*
                return session.createTextMessage(message);
            }
        });

    }

    public void sendMessage(final Destination destination, final Serializable obj) {
        //未使用MessageConverter的情况
		*//*jmsTemplate.send(destination, new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				ObjectMessage objMessage = session.createObjectMessage(obj);
				return objMessage;
			}
			
		});*//*
        //使用MessageConverter的情况
        jmsTemplate.convertAndSend(destination, obj);
       *//* jmsTemplate.execute(new SessionCallback<Object>() {

            public Object doInJms(Session session) throws JMSException {
                MessageProducer producer = session.createProducer(destination);
                Message message = session.createObjectMessage(obj);
                producer.send(message);
                return null;
            }

        });
        jmsTemplate.execute(new ProducerCallback<Object>() {

            public Object doInJms(Session session, MessageProducer producer)
                    throws JMSException {
                Message message = session.createObjectMessage(obj);
                producer.send(destination, message);
                return null;
            }

        });*//*
    }
*/
}
