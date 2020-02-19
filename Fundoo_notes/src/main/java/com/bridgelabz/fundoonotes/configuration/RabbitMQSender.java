package com.bridgelabz.fundoonotes.configuration;
/*
 * author:Lakshmi Prasad A
 */
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.response.MailObject;

@Component
public class RabbitMQSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Value("rube.key")
	private String routingkey;
	@Value("rmq.rube.exchange")
	private String exchange;

	public void send(MailObject message) {
		rabbitTemplate.convertAndSend(exchange, routingkey, message);
	}
}
