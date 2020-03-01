package me.tlgm;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.annotation.Nullable;
import java.util.List;

public class MessageMaker {

    private SendMessage sendMessage;

    public SendMessage makeSendMessage(String text, Message message, List<KeyboardRow> keyboardRows) {
        initMessage(text, message);
        if (keyboardRows != null) {
            setButtons(keyboardRows);
        }
        return sendMessage;
    }

    public SendMessage makeSendMessage(String text, Message message) {
        initMessage(text, message);
        return sendMessage;
    }

    private void initMessage(String text, Message message) {
        sendMessage = new SendMessage()
                .setChatId(message.getChatId())
                .setReplyToMessageId(message.getMessageId())
                .setText(text)
                .enableMarkdown(true)
                .enableMarkdownV2(true);
    }

    private synchronized void setButtons(List<KeyboardRow> keyboardRows) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}
