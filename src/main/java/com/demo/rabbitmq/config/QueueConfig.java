package com.demo.rabbitmq.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 队列配置
 * @author fengyue
 */
@Configuration
public class QueueConfig {

	/*对列名称*/
    public static final String QUEUE_NAME1 = "first-queue";
    public static final String QUEUE_NAME2 = "second-queue";
    public static final String QUEUE_NAME3 = "third-queue";
 
    @Bean
    public Queue firstQueue() {
        /**
         durable="true" 持久化消息队列 ， rabbitmq重启的时候不需要创建新的队列
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
         exclusive  表示该消息队列是否只在当前connection生效,默认是false
         */
    	/**
    	 * 队列名称，是否持久化，是否没有使用将被自动删除，是否只在当前connection生效
    	 */
       return new Queue(QUEUE_NAME1,true,false,false);
    }
 
    @Bean
    public Queue secondQueue() {
        return new Queue(QUEUE_NAME2,true,false,false);
    }
 
    @Bean
    public Queue thirdQueue() {
        // 配置 自动删除
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 60000);//60秒自动删除
        return new Queue(QUEUE_NAME3,true,false,true,arguments);
    }
}

