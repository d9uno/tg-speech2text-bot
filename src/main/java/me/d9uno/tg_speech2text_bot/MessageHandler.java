package me.d9uno.tg_speech2text_bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageHandler extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) return;
        try {
            var receivedMessage = update.getMessage();
            var filePathOnServer = new String();

            if (receivedMessage.hasVoice())
                filePathOnServer = execute(new GetFile(receivedMessage.getVoice().getFileId())).getFilePath();
            else if (receivedMessage.hasVideoNote())
                filePathOnServer = execute(new GetFile(receivedMessage.getVideoNote().getFileId())).getFilePath();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }
    
}
