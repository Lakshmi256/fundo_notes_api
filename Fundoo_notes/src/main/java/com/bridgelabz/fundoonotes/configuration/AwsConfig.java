package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsConfig {

	private String awsKeyId = System.getenv("awsKeyId");
	private String acessKey = System.getenv("acessKey");
	private String region = System.getenv("region");

	@Bean
	public AmazonS3 awsS3() {
		BasicAWSCredentials awscreds = new BasicAWSCredentials(awsKeyId, acessKey);
		return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(region))
				.withCredentials(new AWSStaticCredentialsProvider(awscreds)).build();

	}

}
