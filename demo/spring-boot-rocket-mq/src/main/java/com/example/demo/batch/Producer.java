package com.example.demo.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("172.26.239.135:9876;172.26.239.136:9876");
        //3.启动producer
        producer.start();

        List<Message> msgs = new ArrayList<Message>();

        //4.创建消息对象，指定主题Topic、Tag和消息体
        /**
         * 参数一：消息主题Topic
         * 参数二：消息Tag
         * 参数三：消息内容
         */
        Message msg1 = new Message("BatchTopic", "Tag1", ("批量消息1").getBytes());
        msgs.add(msg1);
        Message msg2 = new Message("BatchTopic", "Tag1", ("批量消息2").getBytes());
        msgs.add(msg2);
        Message msg3 = new Message("BatchTopic", "Tag1", ("批量消息3").getBytes());
        msgs.add(msg3);

        //5.发送消息
        SendResult result = null;

        //TODO：1.直接发送
        result = producer.send(msgs);

        //TODO：2.把大的消息分裂成若干个小的消息
            //ListSplitter splitter = new ListSplitter(msgs);
            //while (splitter.hasNext()) {
            //    try {
            //        List<Message> listItem = splitter.next();
            //        result = producer.send(listItem);
            //    } catch (Exception e) {
            //        e.printStackTrace();
            //    }
            //}

        System.out.println("发送结果:" + result);

        //线程睡1秒
        TimeUnit.SECONDS.sleep(1);

        //6.关闭生产者producer
        producer.shutdown();
    }

}
