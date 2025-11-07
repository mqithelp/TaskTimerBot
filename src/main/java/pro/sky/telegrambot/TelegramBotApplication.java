package pro.sky.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TelegramBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelegramBotApplication.class, args);
	}
/* так. Я сделал БД и пользователя.
БД поднял локально в докере
application.properties заполнил, но в добавил его в гитигнор
Поэтому пишу камент чтобы запушить :-)
На данном этапе проект запустился и в консоли видны сообщения боту
 */
}
