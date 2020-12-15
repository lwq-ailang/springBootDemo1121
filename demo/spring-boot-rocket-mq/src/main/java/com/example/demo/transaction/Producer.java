package com.example.demo.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        //TODO: TransactionMQProducer 创建事务消息
        //1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("group5");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("172.26.239.135:9876;172.26.239.136:9876");
        //TODO:添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            //TODO: 执行本地事务，并回应消息队列的检查请求，返回三个事务状态之一。 -- 3.ExecuteLocalTransaction
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                if (StringUtils.equals("TAGA", msg.getTags())) {
                    //TODO: 提交 -- 4.commit
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TAGB", msg.getTags())) {
                    //TODO: 回滚 -- 4.rollback : 删除消息，mq消费不到
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (StringUtils.equals("TAGC", msg.getTags())) {
                    //TODO: 中间状态不做处理，回查checkLocalTransaction()方法 -- 5.check back when the fourth step confirmation is not received
                    return LocalTransactionState.UNKNOW;
                }
                //TODO:
                return LocalTransactionState.UNKNOW;
            }

            //TODO:该方法时MQ进行消息事务状态回查 -- 6.check the state of local transaction
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("消息的Tag:" + msg.getTags());
                //TODO：提交 -- 7.commit or rollback according to transaction status
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        //3.启动producer
        producer.start();

        String[] tags = {"TAGA", "TAGB", "TAGC"};
        for (int i = 1; i <= 3; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("TransactionTopic", tags[i], ("事务消息" + i).getBytes());
            //TODO: sendMessageInTransaction 在事务控制下发送消息 -- 1.SendHalf Msg，2.Half Msg Send ok
            //5.发送消息  arg=null 对所有的消息事务监听
            SendResult result = producer.sendMessageInTransaction(msg, null);
            System.out.println("发送结果:" + result);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        //producer.shutdown();
    }
}
