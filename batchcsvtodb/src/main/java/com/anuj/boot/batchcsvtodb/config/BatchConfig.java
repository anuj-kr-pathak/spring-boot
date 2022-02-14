package com.anuj.boot.batchcsvtodb.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.anuj.boot.batchcsvtodb.model.Product;

@Configuration
public class BatchConfig {
	
	//configure the step related 
	@Autowired
	private JobBuilderFactory jbf;
	
	@Autowired
	private StepBuilderFactory sbf;
	
	@Bean
	public Job job() {
		return jbf.get("j2")
		.incrementer(new RunIdIncrementer())
		.start(step())
		.build();
	}
	
	@Bean
	public Step step() {
		return sbf.get("s1").<Product,Product>chunk(3)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	
	//configure read function 
	@Bean
	public ItemReader<Product> reader(){
		
		FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("product.csv"));
		
		DefaultLineMapper<Product> lineMapper= new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("id","name","description","price");
		BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Product.class);
		
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		
		reader.setLineMapper(lineMapper);
		
		return reader;
	}
	
	//configure processor
	
	@Bean
	public ItemProcessor<Product, Product> processor(){
		return (p) -> {
			p.setPrice(p.getPrice()-(p.getPrice()/10));
			return p;
		};
	}
	
	//configure writer
	@Bean
	public ItemWriter<Product> writer(){
		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
		writer.setSql("INSERT INTO product (ID,NAME,DESCRIPTION,PRICE) VALUES (:id, :name, :description, :price)");
		writer.setDataSource(datasource());
		return writer;
	}
	
	//configure the datasource 
	@Bean
	public DataSource datasource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mydb");
		dataSource.setUsername("root");
		dataSource.setPassword("yourpassword");
		return dataSource;
	}
	
}
