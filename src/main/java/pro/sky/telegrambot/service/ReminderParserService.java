package pro.sky.telegrambot.service;

import pro.sky.telegrambot.dto.ParseReminderMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReminderParserService {

    public ReminderParserService() {
    }

    public ParseReminderMessage parser(String messageForParsing) {
        LocalDateTime dateTime;
        String date = "";
        String text = "";
        String patternText = "(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)";

        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(messageForParsing);

        if (matcher.matches()) {
            date = matcher.group(1);
            text = matcher.group(3);
            dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            return new ParseReminderMessage(dateTime, text, true);
        }
        return new ParseReminderMessage("Текст не соответствует шаблону\n" + messageForParsing);

    }

}
