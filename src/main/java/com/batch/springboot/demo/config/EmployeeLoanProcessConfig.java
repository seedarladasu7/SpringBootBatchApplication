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
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.entity.LoanAccount;
import com.batch.springboot.demo.processor.ValidateLoanAccountProcessor;
import com.batch.springboot.demo.processor.ValidationEmployeeProcessor;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class EmployeeLoanProcessConfig {

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
	@Qualifier("readCSVJob")
	private Job readCSVJob;

	@Autowired
	@Qualifier("emiJob")
	private Job emiJob;

	//@Scheduled(cron = "0 */1 * * * ?")
	public void performReadCSVFileJob() throws Exception {
		System.out.println("Starting the job: readCSVFileJob");
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(readCSVJob, params);
	}

	@Bean(name = "readCSVJob")	
	public Job readCSVFileJob() {
		return jobBuilderFactory.get("readCSVFileJob").incrementer(new RunIdIncrementer()).start(readCSVFileStep()).build();
	}

	@Bean
	public Step readCSVFileStep() {
		return stepBuilderFactory.get("readCSVFileStep").<Employee, Employee>chunk(5).reader(readCSVFileReader()).processor(readCSVFileProcessor())
				.writer(jpaEmployeeFromCSVFileWriter()).build();
	}

	@Bean
	public ItemProcessor<Employee, Employee> readCSVFileProcessor() {
		return new ValidationEmployeeProcessor();
	}

	@Bean
	public FlatFileItemReader<Employee> readCSVFileReader() {
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
		lineTokenizer.setNames("userName", "gender", "age", "pan", "aadhar", "salary");
		lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5);
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Employee.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public JpaItemWriter<Employee> jpaEmployeeFromCSVFileWriter() {
		JpaItemWriter<Employee> jpaEmpWriter = new JpaItemWriter<>();
		jpaEmpWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaEmpWriter;
	}

	@Bean
	public JdbcBatchItemWriter<Employee> writer() {
		JdbcBatchItemWriter<Employee> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql(
				"INSERT INTO EMPLOYEE (USERNAME, GENDER, AGE, PAN, AADGAR, SALARY) VALUES (:userName, :gender, :age, :pan, :aadhar, :salary)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
		return itemWriter;
	}
	
	
	
	@Scheduled(cron = "*/15 * * * * ?")
	public void performEmiPaymentJob() throws Exception {
		System.out.println("Starting the job: emiPaymentJob");
		
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(emiJob, params);
	}

	@Bean(name = "emiJob")
	public Job emiPaymentJob()  throws Exception {
		return jobBuilderFactory.get("emiPaymentJob").incrementer(new RunIdIncrementer()).start(emiPaymentStep()).build();
	}

	@Bean
	public Step emiPaymentStep()  throws Exception {
		return stepBuilderFactory.get("emiPaymentStep").<LoanAccount, LoanAccount>chunk(5).reader(jpaEmiPaymentReader()).processor(emiPaymentProcessor())
				.writer(jpaLoanAccountWriter()).build();
	}

	@Bean
	public ItemProcessor<LoanAccount, LoanAccount> emiPaymentProcessor() {
		return new ValidateLoanAccountProcessor();
	}
	
	@Bean
    public ItemReader<LoanAccount> jpaEmiPaymentReader() throws Exception {
        String jpqlQuery = "from LoanAccount where status='open'";
    		JpaPagingItemReader<LoanAccount> reader = new JpaPagingItemReader<>();
    		reader.setQueryString(jpqlQuery);
    		reader.setEntityManagerFactory(entityManagerFactory);
    		reader.setPageSize(3);
    		reader.afterPropertiesSet();
    		reader.setSaveState(true);

    		return reader;
    }

	@Bean
	public JpaItemWriter<LoanAccount> jpaLoanAccountWriter() {
		JpaItemWriter<LoanAccount> jpaEmpWriter = new JpaItemWriter<>();
		jpaEmpWriter.setEntityManagerFactory(entityManagerFactory);
		return jpaEmpWriter;
	}

	/*@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setPackagesToScan("com.sid.springbatch");
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter());
        lef.setJpaProperties(new Properties());
        return lef;
    }


    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setGenerateDdl(true);
        jpaVendorAdapter.setShowSql(false);

        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        return jpaVendorAdapter;
    }*/
}