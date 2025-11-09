package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import pro.sky.telegrambot.dto.ParseReminderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import pro.sky.telegrambot.service.NotificationTaskService;
import pro.sky.telegrambot.service.ReminderParserService;

import javax.annotation.PostConstruct;
import java.util.List;


@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private NotificationTaskRepository repository;

    @Autowired
    private ReminderParserService reminderParserService;

    @Autowired
    private NotificationTaskService notificationTaskService;

    private ParseReminderMessage parseReminderMessage;


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        final String START = "/start";
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            String chatId = update.message().chat().id().toString();

            if (update.message().text().equals(START)) {
                SendMessage message = new SendMessage(chatId, "Добро пожаловать в наш теплый ламповый ботик!\n" +
                        "Формат сообщения такой:\n01.01.2022 20:00 Сделать домашнюю работу");
                telegramBot.execute(message);
            } else {
                parseReminderMessage = reminderParserService.parser(update.message().text());
                notificationTaskService.creteReminder(parseReminderMessage, chatId);

                if (parseReminderMessage.isValid()) {
                    SendMessage message = new SendMessage(chatId, "Напоминание добавлено в БД.");
                    telegramBot.execute(message);
                } else {
                    SendMessage message = new SendMessage(chatId, "Ошибочка в задании напоминания. Не добавлено в БД\n" +
                            parseReminderMessage.getErrorMessage());
                    telegramBot.execute(message);
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}