package com.yinqiyun.redlichee.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.util.backoff.ExponentialBackOff;

import javax.jms.Session;

/**
 * Created by LZM on 2016/7/28.
 */
@EnableJms
@Configuration
@PropertySource(value = "classpath:/config/activeMQ.properties")
public class configMQ {
    @Value("${spring.activemq.broker-url}")
    private String bokeURL;

    @Bean(name = "targetConnectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
//        activeMQConnectionFactory.setBrokerURL("tcp://192.168.1.133:61616");
        activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
        activeMQConnectionFactory.setUserName("admin");
        activeMQConnectionFactory.setPassword("admin");
        // 允许你关掉所有类安全检查和信任
        activeMQConnectionFactory.setTrustAllPackages(true);
        return activeMQConnectionFactory;
    }

    //默认缓存1，可以调大，
    @Bean(name = "connectionFactory")
    public CachingConnectionFactory connectionFactory(){
       return new CachingConnectionFactory(activeMQConnectionFactory());
    }
    @Bean(name = "emailqueue")
    public ActiveMQQueue emailMQ(){
        return new ActiveMQQueue("emailqueue");
    }
    @Bean(name = "abc")
    public ActiveMQQueue activeMQQueue(){
        return new ActiveMQQueue("abc");
    }
    @Bean(name = "responseQueue")
    public ActiveMQQueue responseQueue(){
        return new ActiveMQQueue("responseQueue");
    }
//    ---topic
    @Bean(name = "testTopic")
    public ActiveMQTopic testTopic(){
        return new ActiveMQTopic("testTopic");
    }

    //转换
    @Bean(name = "messageConverter")
    public MessageConverterUtil messageConverterUtil(){
        return new MessageConverterUtil();
    }

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setMessageConverter(messageConverterUtil());
//       可以设置事物，是否保存到kahaDB ， 接收方式
        return jmsTemplate;
    }

    @Bean
    public DefaultMessageListenerContainer topic1MessageListenerContainer(){
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        //默认7次,,事物 ，重试过后，就出现消费完。否则出现未消费队列中
        defaultMessageListenerContainer.setSessionTransacted(false); //default false
//        defaultMessageListenerContainer.set/
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
        defaultMessageListenerContainer.setDestination(testTopic());
        defaultMessageListenerContainer.setMessageListener(new TestTopicListener());
        return defaultMessageListenerContainer;
    }
    //----------topic
    @Bean
    public DefaultMessageListenerContainer topic2MessageListenerContainer(){
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        //默认7次,,事物 ，重试过后，就出现消费完。否则出现未消费队列中
        defaultMessageListenerContainer.setSessionTransacted(false); //default false
//        defaultMessageListenerContainer.set/
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory());
        defaultMessageListenerContainer.setDestination(testTopic());
        defaultMessageListenerContainer.setMessageListener(new TestTopicListener1());
//        defaultMessageListenerContainer.setPubSubDomain(true);
        return defaultMessageListenerContainer;
    }


    //--------------------

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory () {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        factory.setConcurrency("1");
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
/*        ExponentialBackOff exponentialBackOff = new ExponentialBackOff();
        exponentialBackOff.start();
        factory.setBackOff(exponentialBackOff);*/
        return factory;
    }

}
