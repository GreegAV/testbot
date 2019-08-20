package com.obrttestbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {

    public static int getNewScreenNumber(String messageFromTheButton) {
        switch (messageFromTheButton) {
            case "Расходы":
                return 1;

            case "На персонал":
                return 11;
            case "Зарплата":
                return 111;
            case "Обучение":
                return 112;

            case "Хозяйственные":
                return 12;
            case "Клининг":
                return 121;
            case "Ремонты":
                return 122;
            case "Хозтовары":
                return 123;

            case "Коммуналка":
                return 13;
            case "Аренда":
                return 131;
            case "Электричество":
                return 132;
            case "Вода":
                return 133;
            case "Обслуживание":
                return 134;

            case "Офис":
                return 14;
            case "Канцтовары":
                return 141;
            case "Связь":
                return 142;


            case "Маркетинг":
                return 16;
            case "Instagram":
                return 161;
            case "Google":
                return 162;
            case "Facebook":
                return 163;
            case "CRM":
                return 164;
            case "Энванто":
                return 165;
            case "Тильда":
                return 166;
            case "Прочее":
                return 167;

            case "Налоги":
                return 17;
            case "ЕСВ":
                return 171;
            case "НДФЛ":
                return 172;
            case "Военный сбор":
                return 173;
            case "Единый 3%-5%":
                return 174;
            case "НДС":
                return 175;

            case "Другие расходы":
                return 10;

            case "Доходы":
                return 2;
            case "Выручка":
                return 21;
            case "Прочие доходы":
                return 20;

        }
        return 0;
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId, int screenNumber) {
        switch (screenNumber) {
            case Config.WECOME_SCREEN: {

                InlineKeyboardMarkup inlineKeyboardMarkup0 = new InlineKeyboardMarkup();

                InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
                inlineKeyboardButton01.setText("Расходы");
                inlineKeyboardButton01.setCallbackData("Расходы");
                inlineKeyboardButton02.setText("Доходы");
                inlineKeyboardButton02.setCallbackData("Доходы");

                List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();

                keyboardButtonsRow01.add(inlineKeyboardButton01);
                keyboardButtonsRow01.add(inlineKeyboardButton02);

                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(keyboardButtonsRow01);

                inlineKeyboardMarkup0.setKeyboard(rowList);
                return new SendMessage().setChatId(chatId).setText("Выберите действие.").setReplyMarkup(inlineKeyboardMarkup0);
            }

            case Config.EXPENSES_SCREEN: {
                InlineKeyboardMarkup inlineKeyboardMarkup1 = new InlineKeyboardMarkup();

                InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton14 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton16 = new InlineKeyboardButton();
                InlineKeyboardButton inlineKeyboardButton17 = new InlineKeyboardButton();

                inlineKeyboardButton11.setText("На персонал");
                inlineKeyboardButton11.setCallbackData("На персонал");
                inlineKeyboardButton12.setText("Хозяйственные");
                inlineKeyboardButton12.setCallbackData("Хозяйственные");
                inlineKeyboardButton13.setText("Коммуналка");
                inlineKeyboardButton13.setCallbackData("Коммуналка");
                inlineKeyboardButton14.setText("Офис");
                inlineKeyboardButton14.setCallbackData("Офис");
                inlineKeyboardButton15.setText("Сервис");
                inlineKeyboardButton15.setCallbackData("Сервис");
                inlineKeyboardButton16.setText("Маркетинг");
                inlineKeyboardButton16.setCallbackData("Маркетинг");
                inlineKeyboardButton17.setText("Налоги");
                inlineKeyboardButton17.setCallbackData("Налоги");

                List<InlineKeyboardButton> keyboardButtonsRow11 = new ArrayList<>();
                List<InlineKeyboardButton> keyboardButtonsRow12 = new ArrayList<>();
                List<InlineKeyboardButton> keyboardButtonsRow13 = new ArrayList<>();
                List<InlineKeyboardButton> keyboardButtonsRow14 = new ArrayList<>();

                keyboardButtonsRow11.add(inlineKeyboardButton11);
                keyboardButtonsRow11.add(inlineKeyboardButton12);

                keyboardButtonsRow12.add(inlineKeyboardButton13);
                keyboardButtonsRow12.add(inlineKeyboardButton14);

                keyboardButtonsRow13.add(inlineKeyboardButton15);
                keyboardButtonsRow13.add(inlineKeyboardButton16);

                keyboardButtonsRow14.add(inlineKeyboardButton17);

                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(keyboardButtonsRow11);
                rowList.add(keyboardButtonsRow12);
                rowList.add(keyboardButtonsRow13);
                rowList.add(keyboardButtonsRow14);

                inlineKeyboardMarkup1.setKeyboard(rowList);
                return new SendMessage().setChatId(chatId).setText("Выберите категорию расходов.").setReplyMarkup(inlineKeyboardMarkup1);
            }
            default: {
                return new SendMessage().setChatId(chatId).setText("Категория " + String.valueOf(Config.screenNumber));
            }
        }
//        return null; //TODO ?!?!???!
    }
}
