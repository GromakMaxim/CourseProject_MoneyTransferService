package com.example.mtservice1.config;

import com.example.mtservice1.repository.TransactionRepository;
import com.example.mtservice1.service.CardValidator;
import com.example.mtservice1.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Bean
    public TransactionRepository repository() {
        return new TransactionRepository();
    }
    @Bean
    public MyService service(TransactionRepository repository) {
        return new MyService(repository);
    }
    @Bean
    public CardValidator cardValidator() {
        return new CardValidator();
    }

}
