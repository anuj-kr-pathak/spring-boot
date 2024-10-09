package com.example.assesment.job;

import com.example.assesment.mapper.SimDetailFieldMapper;
import com.example.assesment.model.Sim;
import com.example.assesment.service.impl.SIMDataReader;
import com.example.assesment.service.impl.CustomJobIncrementer;
import com.example.assesment.service.impl.SIMDataProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
public class SIMDataJobListener extends DefaultBatchConfigurer implements ApplicationContextAware {

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public DataSource dataSource;
    @Autowired
    public JobExplorer jobExplorer;
    @Autowired
    public JobRegistry jobRegistry;
    @Autowired
    public JobLauncher jobLauncher;
    @Autowired
    public JobRepository jobRepository;

    private ApplicationContext applicationContext;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistrar() throws Exception {
        JobRegistryBeanPostProcessor registrar = new JobRegistryBeanPostProcessor();
        registrar.setJobRegistry(this.jobRegistry);
        registrar.setBeanFactory(this.applicationContext.getAutowireCapableBeanFactory());
        registrar.afterPropertiesSet();
        return registrar;
    }

    @Bean
    public JobOperator jobOperator() throws Exception{
        SimpleJobOperator simpleJobOperator = new SimpleJobOperator();
        simpleJobOperator.setJobLauncher(this.jobLauncher);
        simpleJobOperator.setJobParametersConverter(new DefaultJobParametersConverter());
        simpleJobOperator.setJobRepository(this.jobRepository);
        simpleJobOperator.setJobExplorer(this.jobExplorer);
        simpleJobOperator.setJobRegistry(this.jobRegistry);
        return simpleJobOperator;
    }

    @Deprecated
    @Bean
    public FlatFileItemReader<Sim> simFileReader() {
        FlatFileItemReader<Sim> simDetailReader = new FlatFileItemReader<>();
        simDetailReader.setLinesToSkip(13);
        simDetailReader.setResource(new ClassPathResource("/inputs/sim_data1.txt"));
        DefaultLineMapper<Sim> simDefaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer simDetailDelimitedLineTokenizer =  new DelimitedLineTokenizer();
        simDetailDelimitedLineTokenizer.setNames(new String[] {"skip_column","IMSI","PIN1","PUK1","PIN2","PUK2","AAM1","Ki_UMTS_enc","ACC"});
        simDefaultLineMapper.setLineTokenizer(simDetailDelimitedLineTokenizer);
        simDefaultLineMapper.setFieldSetMapper(new SimDetailFieldMapper());
        simDefaultLineMapper.afterPropertiesSet();
        simDetailReader.setLineMapper(simDefaultLineMapper);
        return simDetailReader;

    }

    @Bean
    public JdbcBatchItemWriter<Sim> simDataWriter(){

        JdbcBatchItemWriter<Sim> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(this.dataSource);
        itemWriter.setSql("INSERT INTO sim VALUES (:iMSI, :pIN1, :pUK1, :pIN2, :pUK2, :aAM1, :kiUMTSEnc, :acc )");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        itemWriter.afterPropertiesSet();
        return itemWriter;

    }
//    @Deprecated
//    @Bean
//    public JobParametersIncrementer runIdIncrementer() {
//        return new RunIdIncrementer();
//    }


    @Bean
    public SIMDataReader customFileItemReader() {
        try {
            return new SIMDataReader();
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize reader", e);
        }
    }

    @Bean
    public Step stepSimDetailExecutionFileReader(){

        return  stepBuilderFactory.get("step1")
                .<Sim, Sim> chunk(10)
                //.reader(simDetailFileReader())
                .reader(customFileItemReader())
                .processor(customFileItemProcessor())
                .writer(simDataWriter())
                .build();

    }

    @Bean
    public ItemProcessor<? super Sim,? extends Sim> customFileItemProcessor() {
        return new SIMDataProcessor();
    }

    @Bean
    public Job job() {

        return jobBuilderFactory.get("simJob")
                .incrementer(new CustomJobIncrementer())
                .start(stepSimDetailExecutionFileReader())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                    }
                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        String outputFilePath = (String) jobExecution.getJobParameters().getParameters().get("ouputFilePath").getValue();
                        if (BatchStatus.FAILED.equals(jobExecution.getStatus()) || BatchStatus.ABANDONED.equals(jobExecution.getStatus()) ) {
                            outputFilePath = outputFilePath.substring(0,outputFilePath.lastIndexOf('.')) +".nok";
                        } else if(jobExecution.getStatus().equals(BatchStatus.COMPLETED)){
                            outputFilePath = outputFilePath.substring(0,outputFilePath.lastIndexOf('.')) +".ok";
                        }
                        File outputFile = new File(outputFilePath);
                        try {
                            outputFile.createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .build();

    }

    @Override
    public JobLauncher getJobLauncher() {

        SimpleJobLauncher jobLauncher = null;
        try {
            jobLauncher = new SimpleJobLauncher();
            jobLauncher.setJobRepository(jobRepository);
            jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
            jobLauncher.afterPropertiesSet();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jobLauncher;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
