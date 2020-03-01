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
        try {
            handleIncomingMessage(update);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleIncomingMessage(Update update) throws TelegramApiException {
        Message message = update.getMessage();
        String text = message.getText();
        SendMessage sendMessage;
        if (text.startsWith(Comands.START)) {
            sendMessage = makeSendMessage(START_TEXT, message);
            setButtons(sendMessage);
            execute(sendMessage);
            return;
        }
        if (text.startsWith(Comands.HELP)) {
            sendMessage = makeSendMessage(START_TEXT, message);
            setButtons(sendMessage);
            execute(sendMessage);
            return;
        }
        if (text.startsWith(CART_CREDIT)) {
            execute(makeSendMessage("Кредит на карту", message).setReplyMarkup(setButtonsForCart()));
            return;
        }
        if (text.startsWith(CASH_CREDIT)) {
            makeSendMessage(handleCashCredit(text), message);
            return;
        }
        makeSendMessage(message.getText(), message);
    }

    private SendMessage makeSendMessage(String text, Message message) {
        return new SendMessage()
                .setChatId(message.getChatId())
                .setReplyToMessageId(message.getMessageId())
                .setText(text)
                .enableMarkdown(true)
                .enableMarkdownV2(true)
                .setReplyMarkup(setButtonsForCart());
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
        keyboardSecondRow.add(new KeyboardButton(Comands.HELP));
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private InlineKeyboardMarkup setButtonsForCart() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<InlineKeyboardButton>();
        buttons1.add(new InlineKeyboardButton().setText("Отправить заявку").setCallbackData("17"));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
        return markupKeyboard;
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
