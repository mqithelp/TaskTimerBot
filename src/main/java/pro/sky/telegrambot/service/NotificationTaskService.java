package pro.sky.telegrambot.service;

import pro.sky.telegrambot.Entity.NotificationTask;
import pro.sky.telegrambot.dto.ParseReminderMessage;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

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
}