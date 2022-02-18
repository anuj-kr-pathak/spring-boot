package com.anuj.boot.reactive.vaccine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class VaccineProvider {
	
	@Autowired
	private VaccineService service;
	
	public Flux<Vaccine> provideVaccines(){
		return service.getVaccine().map(this::deliver);
	}
	
	private Vaccine deliver(Vaccine vaccine) {
		vaccine.setDelivered(true);
		return vaccine;
	}
	
}
