package by.gexateq.simplerestservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class Config {
    @Bean
    public ExecutorService fixedThreadPool() {
        return Executors.newFixedThreadPool(10);
    }

}
