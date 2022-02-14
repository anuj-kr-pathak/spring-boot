package com.anuj.boot.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${springjms.myQueue}")
	private String queue;
	
	public void send(String message) {
		System.out.println("Message Send ===> "+message);
		//Convert the message into jmsObject and send it to queue.
		jmsTemplate.convertAndSend(queue,message);
	}
	
	public void sendUsingCreater(String message) {
		MessageCreator messageCreator = s-> s.createTextMessage("Hello Spring JMS!!!!");
		//if you want as topic then you need to set below property 
		//jmsTemplate.setPubSubDomain(true);
		jmsTemplate.send(queue,messageCreator);
		
	}
	
}
