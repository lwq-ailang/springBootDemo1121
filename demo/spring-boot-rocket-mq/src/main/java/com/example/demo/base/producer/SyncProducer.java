package com.example.demo.base.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息：会有一个返回结果
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group-sync");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("172.26.239.135:9876;172.26.239.136:9876");
        //3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            //参数一：消息主题Topic
            //参数二：消息Tag
            //参数三：消息内容
            Message msg = new Message("springboot-mq", "tag1", ("Hello World 同步消息" + i).getBytes());
            //5.发送消息
            SendResult result = producer.send(msg);
            //发送状态
            //SendStatus status = result.getSendStatus();
            System.out.println("发送结果:" + result);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        producer.shutdown();
    }
}
