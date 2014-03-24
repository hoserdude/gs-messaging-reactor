package hello;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import reactor.core.Environment;
import reactor.core.Reactor;
import reactor.core.spec.Reactors;
import reactor.spring.context.config.EnableReactor;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableReactor
public class Application implements CommandLineRunner {

	@Bean(name="rootReactor")
    Reactor rootReactor(Environment env) {
        return Reactors.reactor()
                .env(env)
                .dispatcher(Environment.THREAD_POOL)
                .get();
    }
    
    @Autowired
    private Publisher publisher;
    
    @Bean
    Integer numberOfJokes() {
        return 10;
    }
    
    @Bean
    public CountDownLatch latch(Integer numberOfJokes) {
        return new CountDownLatch(numberOfJokes);
    }
    
    @Override
    public void run(String... args) throws Exception {        
        publisher.publishJokes();
    }
    
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}
