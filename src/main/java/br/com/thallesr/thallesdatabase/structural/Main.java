package br.com.thallesr.thallesdatabase.structural;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;

@SpringBootApplication
@Import(DBConf.class)
@Component
@AllArgsConstructor
public class Main {

    ExecutorService es;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }


    @PreDestroy
    public void tearDown() {
        System.out.println("Shutting Down...............the ");
        List<Runnable> list = es.shutdownNow();
        System.out.println("end");
    }
}