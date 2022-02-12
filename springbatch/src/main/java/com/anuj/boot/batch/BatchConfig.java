package com.anuj.boot.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

	@Autowired
	private StepBuilderFactory sbf;
	
	@Autowired
	private JobBuilderFactory jbf;
	
	//configure Job
	@Bean
	public Job job() {
		return jbf.get("job1")
				.incrementer(new RunIdIncrementer()).
				listener(listener()).
				start(step())
				.build();	
	}
	
	//configure step 
	@Bean
	public Step step() {
		return sbf.get("step1")
				.<String,String>chunk(1)
				.reader(reader())
				.writer(writer())
				.processor(process())
				.build();
	}
	
	//configure reader
	@Bean
	public Reader reader() {
		return new Reader();
	}
	
	//configure writer
	@Bean
	public Writer writer() {
		return new Writer();
	}

	//configure process
	@Bean
	public Processor process() {
		return new Processor();
	}

	//configure listener
	@Bean
	public MyJobListner listener() {
		return new MyJobListner();
	}
}
