package com.bridgelabz.fundoonotes.configuration;

import javax.jms.Session;

/*
 * author:Lakshmi Prasad A
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@EnableJms
public class AwsConfig {

	@Value("${awsKeyId}")
	private String awsKeyId;
	@Value("${acessKey}")
	private String acessKey;
	@Value("${region}")
	private String region;

	@Bean
	public AmazonS3 amazonS3Client() {
		BasicAWSCredentials awscreds = new BasicAWSCredentials(awsKeyId, acessKey);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awscreds)).build();

	}

	/*
	 * SQSConnectionFactory connectionFactory = SQSConnectionFactory.builder()
	 * 
	 * .withRegion(Region.getRegion(Regions.US_EAST_2))
	 * .withAWSCredentialsProvider(new
	 * AWSStaticCredentialsProvider(awscreds)).build();
	 * 
	 * @Bean public DefaultJmsListenerContainerFactory jmsListenerContainerFactory()
	 * { DefaultJmsListenerContainerFactory factory = new
	 * DefaultJmsListenerContainerFactory();
	 * factory.setConnectionFactory(this.connectionFactory);
	 * factory.setDestinationResolver(new DynamicDestinationResolver());
	 * factory.setConcurrency("3-10");
	 * factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE); return
	 * factory; }
	 * 
	 * @Bean public JmsTemplate defaultJmsTemplate() { return new
	 * JmsTemplate(this.connectionFactory); }
	 */
}
