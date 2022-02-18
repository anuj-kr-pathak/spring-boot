package com.anuj.boot.reactive.vaccine;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class VaccineService {

	public Flux<Vaccine> getVaccine(){
		return Flux.just(new Vaccine("Pfizer"),
				new Vaccine("J&J"),
				new Vaccine("Covaxin"));
	}
	
}
