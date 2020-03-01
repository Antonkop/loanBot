package me.tlgm;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallbackQueryResolver {

    public AnswerCallbackQuery resolveCallback(CallbackQuery callbackQuery) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery()
                .setCallbackQueryId(callbackQuery.getId())
                .setShowAlert(true)
                .setCacheTime(100);
        if (CallbackData.BID_CART_CALLBACK.name().equals(callbackQuery.getData())) {
            answer.setText("Фио и номер и серию паспорта ф формате ФИО хххх-хххххх");
            return answer;
        }
        if (CallbackData.BID_STATUS_CART_CALLBACK.name().equals(callbackQuery.getData())) {
            answer.setText("Фио и номер и серию паспорта ф формате ФИО хххх-хххххх");
            return answer;
        }
        return answer;
    }
}
