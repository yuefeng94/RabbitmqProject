package com.demo.rabbitmq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息交换机配置
 * @author fengyue
 */
@Configuration
public class ExchangeConfig {
	
	/** 消息交换机1的名字*/
	public final String EXCHANGE_01 = "first_exchange";
	

	/** 消息交换机2的名字*/
	public final String EXCHANGE_02 = "second_exchange";
	
	/** 消息交换机2的名字*/
	public final String EXCHANGE_03 = "topic_exchange";
	
	/**
     *   1.定义direct exchange，绑定first_exchange
     *   2.durable="true" 持久化交换机， rabbitmq重启的时候不需要创建新的交换机
     *   3.direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相关的队列
     *     fanout交换器中没有路由键的概念，他会把消息发送到所有绑定在此交换器上面的队列中。
     *     topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange(EXCHANGE_01,true,false);
        return directExchange;
    }
    
    @Bean
    public DirectExchange directExchange2(){
        DirectExchange directExchange = new DirectExchange(EXCHANGE_02,true,false);
        return directExchange;
    }
    
    /**
	 *   1.定义topic exchange，绑定third_exchange
	 *   2.durable="true" 持久化交换机， rabbitmq重启的时候不需要创建新的交换机
	 *     topic交换器你采用模糊匹配路由键的原则进行转发消息到队列中
	 *     "abc.*":匹配"abc.a"、"abc.b" etc
	 *     "abc.#":匹配"abc.a"、"abc.a.b" etc
	 *     符号#：匹配一个或者多个词 lazy.# 可以匹配 lazy.irs或者lazy.irs.cor
		       符号*：只能匹配一个词 lazy.* 可以匹配 lazy.irs或者lazy.cor
     */
    @Bean
    public TopicExchange topicExchange(){
    	TopicExchange topicExchange = new TopicExchange(EXCHANGE_03,true,false);
        return topicExchange;
    }
	
}
