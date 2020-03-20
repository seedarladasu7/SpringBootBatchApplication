package com.batch.springboot.demo.config;
 
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.processor.ValidationProcessor;
 
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {
     
    @Autowired
    private JobBuilderFactory jobBuilderFactory;	
 
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;
 
    @Value("classPath:/input/Employee_Record_20032020.csv")
    private Resource inputResource;
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    JobLauncher jobLauncher;
      
    @Autowired
    Job job;
    
    
    @Scheduled(cron = "0 */1 * * * ?")
    public void perform() throws Exception
    {
        JobParameters params = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();
        jobLauncher.run(job, params);
    }
 
    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }
 
    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<Employee, Employee>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(jpaEmployeeWriter())
                .build();
    }
     
    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return new ValidationProcessor();
    }
     
    @Bean
    public FlatFileItemReader<Employee> reader() {
        FlatFileItemReader<Employee> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }
 
    @Bean
    public LineMapper<Employee> lineMapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "userName", "gender", "age", "pan", "aadhar", "salary" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2, 3, 4, 5 });
        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
    
    @Bean
    public JpaItemWriter<Employee> jpaEmployeeWriter() {
    	JpaItemWriter<Employee> jpaEmpWriter = new JpaItemWriter<Employee>();
    	jpaEmpWriter.setEntityManagerFactory(entityManagerFactory);
    	return jpaEmpWriter;
    }
    
    @Bean
    public JdbcBatchItemWriter<Employee> writer() {
        JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<Employee>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO EMPLOYEE (USERNAME, GENDER, AGE, PAN, AADGAR, SALARY) VALUES (:userName, :gender, :age, :pan, :aadhar, :salary)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
        return itemWriter;
    }
}