package com.demo.order.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/order/")
public class OrderController {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@GetMapping("msg")
	public void sendMessage(String id, Long number) {
        JSONObject json = new JSONObject();
        json.put("productId", id);
        json.put("number", number);
		
		//topic模式：发送的key,需要去进行route-key(绑定queue与exchange的key)模糊匹配
		rabbitTemplate.convertAndSend("topic_exchange", "queue.three.1", json.toJSONString(), new CorrelationData("tejslfsewewl"));
	}
	
}
