package pro.sky.telegrambot.service;

import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.telegrambot.Entity.NotificationTask;
import pro.sky.telegrambot.dto.ParseReminderMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository repository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.repository = notificationTaskRepository;
    }

    public void creteReminder(ParseReminderMessage parsed, String chatId) {
        if (parsed.isValid()) {
            NotificationTask task = new NotificationTask();
            task.setChatId(Long.parseLong(chatId));
            task.setReminderTime(parsed.getDateTime());
            task.setMessageText(parsed.getText());
            NotificationTask saveTask = repository.save(task);
        } else {
            System.out.println("Ошибка: " + parsed.getErrorMessage());
        }
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void runCheck() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMinuteAgo = now.minusMinutes(1);
        System.out.println("ReminderBySheduler started: " + LocalDateTime.now());
        List<NotificationTask> tasks =  repository.findByReminderTimeBetween(oneMinuteAgo,now);
        tasks.forEach(task -> {
            System.out.println(task.getReminderTime() + ": " + task.getMessageText());
        });

    }

}