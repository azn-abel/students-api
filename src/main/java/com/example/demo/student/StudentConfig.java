package com.example.demo.student;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public Counter successfulApiCallsCounter(MeterRegistry registry) {
        return Counter.builder("api_calls.successful")
                .description("Number of successful API calls")
                .register(registry);
    }

    @Bean
    public Counter failedApiCallsCounter(MeterRegistry registry) {
        return Counter.builder("api_calls.failed")
                .description("Number of failed API calls")
                .register(registry);
    }

    public 

    // @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "mariam.jamal@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 5)
            );

            Student alex = new Student(
                    "Alex",
                    "alex.jamal@gmail.com",
                    LocalDate.of(2007, Month.FEBRUARY, 20)
            );

            repository.saveAll(
                    List.of(mariam, alex)
            );
        };
    }
}
