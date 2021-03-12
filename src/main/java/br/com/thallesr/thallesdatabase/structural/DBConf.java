package br.com.thallesr.thallesdatabase.structural;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Configuration
public class DBConf {
    @Bean
    Thread mainThread() {
        return new Thread(() -> {
            while (true && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.currentThread().sleep(1000);
                    System.out.println("sleeping");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("get interrupt");
                }
            }
            System.out.println("Correctly terminated");
        });
    }

    @Bean
    ExecutorService exec(Thread mainThread){
        ExecutorService es =Executors.newSingleThreadExecutor();
        es.submit(mainThread);
        return es;
    }
}
