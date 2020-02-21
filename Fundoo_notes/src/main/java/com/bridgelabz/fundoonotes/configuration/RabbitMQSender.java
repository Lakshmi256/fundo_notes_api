package com.bridgelabz.fundoonotes.configuration;

/*
 * author:Lakshmi Prasad A
 */

//import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.response.MailObject;
//import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

@Component
public class RabbitMQSender {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	/*
	 * @Autowired private MailServiceProvider sendingMail;
	 */

	@Value("rmq.rube.exchange")
	private String exchangeName;

	@Value("rube.key")
	private String routingKey;

	public void produceMsg(MailObject message) {
		rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
	}

	/*
	 * @RabbitListener(queues = "${rmq.rube.queue}") public void
	 * rabbitMqlistener(MailObject msg) { sendingMail.sendEmail(msg.getEmail(),
	 * msg.getSubject(), msg.getMessage()); }
	 */
}
