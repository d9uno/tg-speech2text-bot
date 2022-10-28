package me.d9uno.tg_speech2text_bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            var botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new MessageHandler(args[0], args[1]));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
