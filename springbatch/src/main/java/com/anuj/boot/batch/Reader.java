package com.anuj.boot.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class Reader implements ItemReader<String> {

	String [] country =  {"India","Aus","US"};
	private int count;
	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		System.out.println("Inside Reader method");
		if(count<country.length) {
			count=count+1;
			return country[count-1];
		}else {
			count=0;
		}
		
		return null;
	}

}
