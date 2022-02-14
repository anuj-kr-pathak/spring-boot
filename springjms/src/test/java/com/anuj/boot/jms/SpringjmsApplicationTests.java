package com.anuj.boot.jms;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringjmsApplicationTests {

	@Autowired
	MessageSender sender;
	
	@Test
	void testSendAndReceived() {
		sender.send("Hello Spring JMS");
		
	}

}
