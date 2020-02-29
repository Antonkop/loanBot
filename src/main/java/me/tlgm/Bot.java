package me.tlgm;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Bot extends TelegramLongPollingBot {

    private final String BOT_NAME = "LoanBot";
    private final String BOT_TOKEN = "950873857:AAGMygfvRTUVc0fvk4NnX1-9vo5UgT6gVCc";

    public void onUpdateReceived(Update update) {

        String chatId = String.valueOf(update.getMessage().getChatId());
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        sendMessage.setText("ты хуй");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getLocalizedMessage());
            e.printStackTrace();
        }

//        if (update.getMessage() != null) {
//            handleIncomingMessage(update.getMessage());
//        }
    }

    private void handleIncomingMessage(Message message) {
        if (!message.isUserMessage() && message.hasText()) {
            String text = message.getText();
            if (text.startsWith(Comands.ENTER_FIO)) {
                sendMessage(handleFIO(text), message);
            } else if (text.startsWith(Comands.ENTER_PASSPORT)) {
                sendMessage(handlePassport(text), message);
            } else {
                sendMessage(message.getText(), message);
            }
        }
    }

    private void sendMessage(String text, Message message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(message.getChatId());
        sendMessageRequest.setText(text);
        sendMessageRequest.setReplyToMessageId(message.getMessageId());
        sendMessageRequest.enableMarkdown(true);
        setButtons(sendMessageRequest);
        try {
            execute(sendMessageRequest);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }
    }

    private synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Привет"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Помощь"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private String handleFIO(String text) {
        String fio = text.trim().replace(Comands.ENTER_FIO, "");
        // тут будет какая-то логика обработки ФИО
        return fio;
    }

    private String handlePassport(String text) {
        String passpor = text.trim().replace(Comands.ENTER_PASSPORT, "");
        // тут будет какая-то логика обработки серии и номера паспорта
        return passpor;
    }


    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}
