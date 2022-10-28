package me.d9uno.tg_speech2text_bot;

import java.nio.file.Path;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageHandler extends TelegramLongPollingBot {
    private final MediaConverter mediaConverter = new MediaConverter();

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

            var wavFile = mediaConverter.convertAudio(
                downloadFile(filePathOnServer, Path.of("./data", receivedMessage.getChatId().toString(), filePathOnServer).toFile()),
            "wav", "pcm_s16le", 128000, 1, 16000);
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
