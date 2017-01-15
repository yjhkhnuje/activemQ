package com.yinqiyun.redlichee.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.jms.*;

/**
 * Created by LZM on 2016/5/11.
 */
public class EmailConsuMsgListener implements MessageListener {
    private static Logger logger = Logger.getLogger(EmailConsuMsgListener.class);

    @Autowired
    @Qualifier("responseQueue")
    private Destination responseDestination;

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
            TextMessage textMsg = (TextMessage) message;
            try {
                logger.info("消费文本emial--" + textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            throw  new RuntimeException("aaaa");
        } else if (message instanceof MapMessage) {
            MapMessage mapMessage = (MapMessage) message;
            logger.error("消费emial，接收到一个MapMessage" + mapMessage);
        } else if (message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            try {
                System.out.println("接收到一个emial,");
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new RuntimeException("123");
        } else {
            logger.error("消费emial，不知类型数据" + message);
        }
    }

}
