package com.yinqiyun.redlichee.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.annotation.JmsListeners;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * Created by LZM on 2016/7/28.
 */
@Component
public class Consumer {
    @Autowired
    @Qualifier("responseQueue")
    private Destination responseDestination;

    @JmsListener(destination = "testqueue")
    public void receiveQueue(Message message) throws JMSException {
        ObjectMessage message1 = (ObjectMessage) message;
        System.out.println(message1.getObject().toString());
        message1.setJMSReplyTo(responseDestination);
//        throw  new RuntimeException("1111");
    }

/*
    @JmsListener(destination = "testTopic",subscription = "testTopic")
    public void receiveTopic1(Message message) throws JMSException {
        TextMessage message1 = (TextMessage) message;
        System.out.println(message1.getText().toString());
        System.out.println(1111);
//        message1.setJMSReplyTo(responseDestination);
//        throw  new RuntimeException("1111");
    }
*/


    @JmsListener(destination = "testTopic",subscription = "testTopic")
    public void receiveTopic2(Message message) throws JMSException {
        TextMessage message1 = (TextMessage) message;
        System.out.println(message1.getText().toString());
        System.out.println(2222);
    }


}
