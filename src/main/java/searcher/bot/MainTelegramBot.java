package searcher.bot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer  {
    private final TelegramClient telegramClient;

    public MainTelegramBot() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText() && message.getText().startsWith("/")) {
                long chatId = message.getChatId();

                try {
                    handleCommand(message.getText(), chatId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            String data = update.getCallbackQuery().getData();
            int messageId = update.getCallbackQuery().getMessage().getMessageId();

            if (data.equalsIgnoreCase("start")) {
                EditMessageText editMessageText = EditMessageText.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .text("Server works")
                        .build();

                try {
                    telegramClient.execute(editMessageText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void handleCommand(String command, long id) throws Exception {
        switch (command) {
            case "/start": {
                SendMessage sendMessage = SendMessage.builder()
                        .text(Commands.START_ANSWER.formatted(id))
                        .chatId(id)
                        .replyMarkup(InlineKeyboardMarkup.builder()
                                .keyboardRow(new InlineKeyboardRow(
                                        InlineKeyboardButton.builder()
                                                .text("Начать")
                                                .callbackData("start")
                                                .build()
                                ))
                                .build())
                        .build();

                telegramClient.execute(sendMessage);
                break;
            }
        }
    }

    @Override
    public String getBotToken() {
        return "5797005497:AAFyf4UyM4wE8DwgX49AUEtUI8GcbVmlO6I";
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}