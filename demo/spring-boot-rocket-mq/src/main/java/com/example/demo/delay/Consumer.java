package com.example.demo.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws Exception {
        //1.创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.指定Nameserver地址
        consumer.setNamesrvAddr("172.26.239.135:9876;172.26.239.136:9876");
        //3.订阅主题Topic和Tag
        consumer.subscribe("DelayTopic", "*");

        //4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            //接受消息内容
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    //msg.getStoreTimestamp() 获取MQ时间
                    System.out.println("消息ID：【" + msg.getMsgId() + "】,延迟时间：" + (System.currentTimeMillis() - msg.getStoreTimestamp()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动消费者consumer
        consumer.start();
        System.out.println("消费者启动");
    }
}
