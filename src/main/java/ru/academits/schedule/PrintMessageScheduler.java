package ru.academits.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PrintMessageScheduler {
    @Scheduled(fixedRate = 100000)
    public void PrintMessage(){
        System.out.println("Scheduler is triggered");
    }
}
