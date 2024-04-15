package by.gexateq.simplerestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SimpleRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleRestServiceApplication.class, args);
    }

}
