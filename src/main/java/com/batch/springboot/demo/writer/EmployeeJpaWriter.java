package com.batch.springboot.demo.writer;

import java.util.List;

import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.springboot.demo.entity.Employee;
import com.batch.springboot.demo.repository.EmployeeRepository;
import com.batch.springboot.demo.service.EmployeeService;

@Component
public class EmployeeJpaWriter<T> implements ItemWriter<Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeJpaWriter.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {
        log.info("Mailing messages digests and updating messages notification statuses");
        
        /*
        employees.stream().map(mapper)
        

        for (UserAccount userAccount : userAccounts) {
            if (userAccount.isEmailNotification()) {
                mailerService.mailMessagesDigest(userAccount);
            }
            for (Message message : userAccount.getReceivedMessages()) {
                message.setNotificationSent(true);
                messageRepository.save(message);//NOT SAVING!!
            }
        } */
    }
}