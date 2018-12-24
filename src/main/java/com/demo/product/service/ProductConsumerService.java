package com.demo.product.service;

import java.util.Optional;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.demo.dao.ProductMongoRepository;
import com.demo.entity.OrderDetail;
import com.demo.entity.Product;
import com.demo.utils.EntityFactory;
import com.rabbitmq.client.Channel;

@Service
public class ProductConsumerService {

	@Autowired
	private ProductMongoRepository productRepository;
	
	@RabbitListener(queues = {"first-queue"})
	public void handleMessage(Message message, Channel channel) throws Exception {
		String jsonStr = new String(message.getBody());
		OrderDetail orderDetail = JSON.parseObject(jsonStr, OrderDetail.class);
		
		Optional<Product> optionalProduct = this.productRepository.findById(orderDetail.getProductId());
		
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			
			/**
			 * 判断库存是否充足，
			 * 若充足，则扣减商品数量后，更新库存量
			 * 否则，说明库存量不足
			 */
			if (product.getNumber() - orderDetail.getNumber() < 0) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				throw new Exception("库存不足");
			} else {
				product.setNumber(product.getNumber() - orderDetail.getNumber());
				this.productRepository.save(product);
			}
		} else {
			Product product = EntityFactory.getEntity(Product.class);
			product.setNumber(orderDetail.getNumber());
			this.productRepository.insert(product);
		}
	}
}
