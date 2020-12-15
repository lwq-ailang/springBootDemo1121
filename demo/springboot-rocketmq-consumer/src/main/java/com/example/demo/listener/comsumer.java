package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component //注册到springboot容器中
@RocketMQMessageListener(topic = "springboot-mq", //消息主题
        //consumeMode = ConsumeMode.ORDERLY, //指定消费模式：ORDERLY[集群(负载均衡)--默认]和CONCURRENTLY[广播--并发]
        consumerGroup = "${rocketmq.producer.group}") //消息组，读取application.properties配置文件
public class comsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("接受到的消息："+message);
    }
}