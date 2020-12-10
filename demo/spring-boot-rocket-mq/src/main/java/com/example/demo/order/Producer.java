package com.example.demo.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class Producer {

    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("172.26.239.135:9876;172.26.239.136:9876");
        //3.启动producer
        producer.start();
        //构建消息集合
        List<OrderStep> orderSteps = OrderStep.buildOrders();
        //发送消息
        for (int i = 0; i < orderSteps.size(); i++) {
            String body = orderSteps.get(i) + "";//获取消息体
            //参数一：消息主题Topic
            //参数二：消息Tag
            //参数三：keys：消息的关键字
            //参数四：消息内容
            Message message = new Message("OrderTopic", "Order", "i" + i, body.getBytes());
            /**
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择队列的业务标识（订单ID）
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 * @param mqs：队列集合（broker里面的队列）
                 * @param msg：消息对象
                 * @param arg：业务标识的参数 = orderSteps.get(i).getOrderId()
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    long orderId = (long) arg;
                    //使用订单id 取模 选择一个队列
                    long index = orderId % mqs.size();
                    return mqs.get((int) index);//返回一个结果
                }
            }, orderSteps.get(i).getOrderId());

            System.out.println("发送结果：" + sendResult);
        }
        producer.shutdown();
    }

}
