package pro.sky.telegrambot.dto;

import java.time.LocalDateTime;

public class ParseReminderMessage {
    private final LocalDateTime dateTime;
    private final String text;
    private final boolean valid;
    private final String errorMessage;

    public ParseReminderMessage(LocalDateTime dateTime, String text, boolean valid) {
        this.dateTime = dateTime;
        this.text = text;
        this.valid = valid;
        this.errorMessage = null;
    }

    public ParseReminderMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.dateTime = null;
        this.text = null;
        this.valid = false;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getText() {
        return text;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
