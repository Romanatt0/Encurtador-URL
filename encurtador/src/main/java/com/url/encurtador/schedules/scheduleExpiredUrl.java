package com.url.encurtador.schedules;

import com.url.encurtador.repositories.UrlRepository;
import org.hibernate.annotations.Comment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class scheduleExpiredUrl {

    private final UrlRepository repository;

    public scheduleExpiredUrl(UrlRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 3000)
    //180000
    public void execute() throws InterruptedException{
        repository.deleteExpired(LocalDate.now());
        System.out.println("URLs expiradas removidas.");
    }
}
