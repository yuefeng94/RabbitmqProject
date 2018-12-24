package com.demo.rabbitmq.config.callback;

import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 消息发送到交换机 回调配置类
 * @author fengyue
 */
public class MsgSendConfirmCallBack implements ConfirmCallback {

	/**
	 * 回调处理方法
	 * @param correlationData 关联回调的相关数据
	 * @param ack  true or false
	 * @param cause 为ack=false时提供可选的原因，如果可用，否则为NULL。
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		//System.out.println("回调ID： " + correlationData);
		/**
		 * 若ack为true,则消息发送到exchange成功
		 * 若ack为false,则消息发送到exchange失败
		 */
		if (ack) {
			//System.out.println("消息发送到exchange成功");
		} else {
			System.out.println("消息发送到exchange失败");
		}
	}

}
