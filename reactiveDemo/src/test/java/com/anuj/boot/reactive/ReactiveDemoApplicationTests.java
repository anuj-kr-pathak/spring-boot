package com.anuj.boot.reactive;

import java.time.Duration;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.anuj.boot.reactive.vaccine.VaccineProvider;
import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
class ReactiveDemoApplicationTests {

	@Autowired
	VaccineProvider provider;
	
	@Test
	void testVacciineProvider() {
		provider.provideVaccines().subscribe(new VaccineConsumer());
	}
	
	
	// mono work with 0 to 1 producer and flux works with multiple producer
	  @Test
	  void testMono() { Mono<String> mono = Mono.just("Mono Ubuntu System");
	  //log all internal event having in process //there should be always
	  //subscriber to flow data 
	  //mono.log().map(data->data.toUpperCase());
	  mono.log().map(data->data.toUpperCase()).subscribe(data->System.out.println(
	  data));
	  
	  }
	  
	  @Test 
	  void testFlux() { Flux<String> flux =
	  Flux.just("Ubuntu System","Ipad","Windows");
	  
	  //log all internal event having in process //there should be always
	  //subscriber to flow data 
	  //mono.log().map(data->data.toUpperCase());
	  flux.log().map(data->data.toUpperCase()).subscribe(data->System.out.println(
	  data));
	  
	  }
	  
	  @Test
	  void testFluxUsingConsumer() { Flux<String> flux =
	  Flux.just("Ubuntu System","Ipad","Windows"); //instead of lamda expression we are using orderConsumer object.
	  flux.log().map(data->data.toUpperCase()).subscribe(new OrderConsumer());
	  
	  }
	 

	@Test
	void testFluxAddDeplay() throws InterruptedException {

		Flux<String> flux = Flux.fromIterable(Arrays.asList("Macbook Pro", "Iphone", "Dell"));
		// instead of lamda expression we are using orderConsumer object.
		flux.delayElements(Duration.ofSeconds(2)).log().map(data -> data.toUpperCase()).subscribe(new OrderConsumer());
		Thread.sleep(6000);
	}

	@Test
	void testFluxUsingSubscriper() throws InterruptedException{

		Flux<String> flux = Flux.fromIterable(Arrays.asList("Macbook Pro", "Iphone", "Dell"));
		// instead of lamda expression we are using orderConsumer object.
		flux.delayElements(Duration.ofSeconds(2))
		.log()
		.map(data -> data.toUpperCase())
		.subscribe(new Subscriber<String>() {

			@Override
			public void onSubscribe(Subscription subscription) {
				//request method send a signal to publisher  and tell how many request at a time it can handle.
				subscription.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(String order) {
				System.out.println(order);
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onComplete() {
				System.out.println("Custom Subscription is Done");
			}
		});
	}
	
	@Test
	void testFluxUsingSubsWithBatching(){

		Flux<String> flux = Flux.fromIterable(Arrays.asList("Macbook Pro", "Iphone", "Dell","Macbook Pro1", "Iphone1", "Dell1","Macbook Pro2", "Iphone2", "Dell2"));
		flux.log()
		.map(data -> data.toUpperCase())
		.subscribe(new Subscriber<String>() {

			private long count = 0;
			private Subscription subscription;
			
			@Override
			public void onSubscribe(Subscription subscription) {
				this.subscription = subscription;
				//request method send a signal to publisher  and tell how many request at a time it can handle.
				subscription.request(3);
			}

			@Override
			public void onNext(String order) {
				//to achieve batching in subscription model.
				count++;
				if(count>=3) {
					count=0;
					subscription.request(3);
				}
				System.out.println(order);
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onComplete() {
				System.out.println("Custom Subscription is Done");
			}
		});
	}
	
}
