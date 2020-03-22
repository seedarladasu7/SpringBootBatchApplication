package com.batch.springboot.demo;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringBootBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBatchApplication.class, args);
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.batch.springboot.demo")).build();
	}
	
	@Bean
    public ThreadPoolTaskExecutor taskExecutor() {
	    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	    taskExecutor.setCorePoolSize(15);
	    taskExecutor.setMaxPoolSize(20);
	    taskExecutor.setQueueCapacity(30);
	    return taskExecutor;
	}
	
	@Bean
    public JobLauncher jobLauncher(ThreadPoolTaskExecutor taskExecutor, JobRepository jobRepository){
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setTaskExecutor(taskExecutor);
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

}