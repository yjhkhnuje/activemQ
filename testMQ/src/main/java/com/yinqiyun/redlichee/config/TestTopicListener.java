package com.yinqiyun.redlichee.config;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Zero on 2016/8/8.
 */
public class TestTopicListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            //这里我们知道生产者发送的就是一个纯文本消息，所以这里可以直接进行强制转换，或者直接把onMessage方法的参数改成Message的子类TextMessage
            TextMessage textMsg = (TextMessage) message;
            try {
                System.out.println("消费文本topic--" + textMsg.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
