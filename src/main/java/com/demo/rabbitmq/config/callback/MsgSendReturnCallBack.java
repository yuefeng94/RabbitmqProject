package com.demo.rabbitmq.config.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;

/**
 * 消息从exchange发送queue 返回回馈配置类
 * @author fengyue
 */
public class MsgSendReturnCallBack implements ReturnCallback {

	/**
     * 当消息从交换机到队列失败时，该方法才会被调用。消息进行回调处理
     * @param message 
     * @param replyCode 
     * @param replyText
     * @param exchange
     * @param routingKey
     */
	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println("消息从exchange到queue失败：" + message);
	}

}
