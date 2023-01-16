package pl.markowski.edoctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class EDoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(EDoctorApplication.class, args);
    }

}
