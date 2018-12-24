package com.demo.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.rabbitmq.config.callback.MsgSendConfirmCallBack;
import com.demo.rabbitmq.config.callback.MsgSendReturnCallBack;

/**
 * rabbitmq配置
 * @author fengyue
 */

@Configuration
public class RabbitmqConfig {
	/**
     * key: queue在该direct-exchange中的key值，当消息发送给direct-exchange中指定key为设置值时，
     * 消息将会转发给queue参数指定的消息队列
     */
	public static final String ROUTING_KEY_1 = "queue_one_key1";
	
	public static final String ROUTING_KEY_2 = "queue_two_key2";
	
	public static final String ROUTING_KEY_3 = "queue.three.*"; 
	
	public static final String ROUTING_KEY_3_1 = "queue.three.#"; 
	
	
	@Autowired
	private QueueConfig queueConfig;
	
	@Autowired
	private ExchangeConfig exchangeConfig;
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	/**
	 * queue和exchange 绑定
	 */
	@Bean(name={"first"})
	public Binding binding_one() {
		Binding binding = BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.directExchange()).with(RabbitmqConfig.ROUTING_KEY_1);
		return binding;
	}
	
	/**
	 * direct模式：
	 * 不同对列绑定在同一交换机上（根据路由key来精确匹配）
	 */
	@Bean(name={"second"})
	public Binding binding_second() {
		return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.directExchange()).with(RabbitmqConfig.ROUTING_KEY_2);
	}
	
	/**
	 * topic模式：
	 * 不同对列绑定在同一交换机上（根据路由key来模糊匹配）
	 */
	@Bean
	public Binding binding_third() {
		return BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.topicExchange()).with(RabbitmqConfig.ROUTING_KEY_3);
	}
	/**
	 * topic模式：
	 * 不同对列绑定在同一交换机上（根据路由key来模糊匹配）
	 */
	@Bean
	public Binding binding_third1() {
		return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.topicExchange()).with(RabbitmqConfig.ROUTING_KEY_3_1);
	}
	
	@Bean
	public RabbitTemplate getRabbitTemplate () {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		/**
		 * 若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
		rabbitTemplate.setConfirmCallback(msgSendConfirmCallBack());
 
        /**
         * 使用return-callback时必须设置mandatory为true，否则不回调
         * 或者在配置中设置mandatory-expression的值为true，可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return-callback’时使用，mandatory-expression与mandatory只能配置其中一个
         */
		rabbitTemplate.setReturnCallback(msgSendReturnCallBack());

		rabbitTemplate.setMandatory(true);

		return rabbitTemplate;
	}
	 /**
     * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
     * 1.如果消息没有到exchange,则confirm回调,ack=false
     * 2.如果消息到达exchange,则confirm回调,ack=true
     * 3.exchange到queue成功,则不回调return
     * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
     */
 
    /**
     * 消息确认机制
     * Confirms客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
     * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
     */
	
	@Bean
	public MsgSendConfirmCallBack msgSendConfirmCallBack() {
		return new MsgSendConfirmCallBack();
	}
	
	@Bean
	public MsgSendReturnCallBack msgSendReturnCallBack() {
		return new MsgSendReturnCallBack();
	}
	
}
