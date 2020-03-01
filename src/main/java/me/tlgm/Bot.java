package me.tlgm;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
    private final String START_TEXT = "Это кредитный бот";
    private final String CASH_CREDIT = "Кредит наличными";
    private final String CART_CREDIT = "Кредит на карту";
    private final String HELP_BUTTON_TEXT = "Помощь";

    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            handleIncomingMessage(update.getMessage());
        }
    }

    private void handleIncomingMessage(Message message) {
        String text = message.getText();
        if (text.startsWith(Comands.START)) {
            sendMessage(START_TEXT, message);
            return;
        }
        if (text.startsWith(Comands.HELP)) {
            sendMessage(HELP_TEXT, message);
            return;
        }
        if (text.startsWith(CART_CREDIT)) {
            sendMessage(handleCartCredit(text), message);
            return;
        }
        if (text.startsWith(CASH_CREDIT)) {
            sendMessage(handleCashCredit(text), message);
            return;
        }
        sendMessage(message.getText(), message);
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
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(HELP_BUTTON_TEXT));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private String handleCartCredit(String text) {
        setButtonsForCart();
        return "работать иди попрашайка";
    }

    private void setButtonsForCart() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<InlineKeyboardButton>();
        buttons1.add(new InlineKeyboardButton().setText("Отправить заявку").setCallbackData("17"));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
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
