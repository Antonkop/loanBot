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
    private final String HELP_TEXT = "помоги себе сам )))";
    private final String START_TEXT = "Это кредитный бот список команд: ";
    private final String CASH_CREDIT = "Кредит наличными";
    private final String CART_CREDIT = "Кредит на карту";

    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            handleIncomingMessage(update.getMessage());
        }
    }

    private void handleIncomingMessage(Message message) {
        String text = message.getText();
        if (text.startsWith(CART_CREDIT)) {
            sendMessage(handleCartCredit(text), message);
        } else if (text.startsWith(CASH_CREDIT)) {
            sendMessage(handleCashCredit(text), message);
        } else {
            sendMessage(message.getText(), message);
        }
    }

    private void sendMessage(String text, Message message) {
        SendMessage sendMessageRequest = new SendMessage();
        sendMessageRequest.setChatId(String.valueOf(message.getChatId()));
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
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(CART_CREDIT));
        keyboardFirstRow.add(new KeyboardButton(CASH_CREDIT));
        keyboard.add(keyboardFirstRow);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private String handleCartCredit(String text) {
        // тут будет какая-то логика обработки ФИО
        return "работать иди попрашайка";
    }

    private String handleCashCredit(String text) {
        return "хуй те с маслом, а не кредит";
    }


    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}
