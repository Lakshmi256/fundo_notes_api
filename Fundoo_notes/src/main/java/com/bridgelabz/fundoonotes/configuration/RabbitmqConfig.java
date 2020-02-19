package com.bridgelabz.fundoonotes.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * author:Lakshmi Prasad A
 */


@Configuration
@EnableRabbit
public class RabbitmqConfig {
	
	@Autowired
	private ConnectionFactory rabbitConnectionFactory;

	@Value("${rmq.rube.exchange}")
	private String exchangeName;

	@Value("${rmq.rube.queue}")
	private String queue;
	
	@Value("${rube.key}")
	private String routingKey;
	
	@Bean
	DirectExchange rubeExchange() {
		return new DirectExchange(exchangeName);
	}

	@Bean
	public Queue rubeQueue() {
		return new Queue(queue, false);
	}

	@Bean
	Binding rubeExchangeBinding(DirectExchange rubeExchange, Queue rubeQueue) {
		return BindingBuilder.bind(rubeExchange).to(rubeExchange).with(routingKey);
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
		RabbitTemplate rt = new RabbitTemplate(rabbitConnectionFactory);
		rt.setExchange(exchangeName);
		rt.setRoutingKey(routingKey);
		rt.setMessageConverter(jsonMessageConverter());
		return rt;
	}

}
