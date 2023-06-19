package it.cr.tasks;

import it.cr.collection.CollectionRunner;
import it.cr.example.dto.SecondRequest;
import it.cr.example.dto.FirstRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ScheduledTask {

    @Autowired
    CollectionRunner cr;

    @Scheduled(timeUnit = TimeUnit.HOURS, fixedRate = 10, initialDelay = 0)
    public void automaticArchive() throws Exception {
        cr.getContext().put("X-CSRF-TOKEN","aaa-bbb-ccc-ddd");
        cr.getContext().put("Cookie","JSESSIONID=eeeeeeeeeeeeee");

        cr.chain((new FirstRequest()));
        cr.chain(new SecondRequest());

        cr.run(10L);
    }
}
