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
                return Config.EXPENSES_SCREEN;

            case "На персонал":
                return Config.EXPENSES_PERSONAL_SCREEN;
            case "Зарплата":
                return Config.EXPENSES_PERSONAL_SALARY;
            case "Обучение":
                return Config.EXPENSES_PERSONAL_STUDY;

            case "Хозяйственные":
                return Config.EXPENSES_HOUSEHOLD_SCREEN;
            case "Клининг":
                return Config.EXPENSES_HOUSEHOLD_CLEANING;
            case "Ремонты":
                return Config.EXPENSES_HOUSEHOLD_REPAIR;
            case "Хозтовары":
                return Config.EXPENSES_HOUSEHOLD_GOODS;

            case "Коммуналка":
                return Config.EXPENSES_COMMUNAL_SCREEN;
            case "Аренда":
                return Config.EXPENSES_COMMUNAL_RENT;
            case "Электричество":
                return Config.EXPENSES_COMMUNAL_ELECTRICITY;
            case "Вода":
                return Config.EXPENSES_COMMUNAL_WATER;
            case "Обслуживание":
                return Config.EXPENSES_COMMUNAL_SERVICE;

            case "Офис":
                return Config.EXPENSES_OFFICE_SCREEN;
            case "Канцтовары":
                return Config.EXPENSES_OFFICE_STATIONARY;
            case "Связь":
                return Config.EXPENSES_OFFICE_CONNECTION;


            case "Маркетинг":
                return Config.EXPENSES_MARKETING_SCREEN;
            case "Instagram":
                return Config.EXPENSES_MARKETING_INSTAGRAMM;
            case "Google":
                return Config.EXPENSES_MARKETING_GOOGLE;
            case "Facebook":
                return Config.EXPENSES_MARKETING_FACEBOOK;
            case "CRM":
                return Config.EXPENSES_MARKETING_CRM;
            case "Энванто":
                return Config.EXPENSES_MARKETING_ENVATO;
            case "Тильда":
                return Config.EXPENSES_MARKETING_TILDA;
            case "Прочее":
                return Config.EXPENSES_MARKETING_OTHER;

            case "Налоги":
                return Config.EXPENSES_TAXES_SCREEN;
            case "ЕСВ":
                return Config.EXPENSES_TAXES_ESV;
            case "НДФЛ":
                return Config.EXPENSES_TAXES_NDFL;
            case "Военный сбор":
                return Config.EXPENSES_TAXES_WAR;
            case "Единый 3%-5%":
                return Config.EXPENSES_TAXES_EDIN;
            case "НДС":
                return Config.EXPENSES_TAXES_VAT;

            case "Другие расходы":
                return Config.EXPENSES_DIFFERENT;

            case "Доходы":
                return Config.INCOME_SCREEN;
            case "Выручка от продаж":
                return Config.INCOME_REVENUE;
            case "Прочие доходы":
                return Config.INCOME_OTHERREVENUE;

        }
        return 0;
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId, int screenNumber) {
        switch (screenNumber) {
            case Config.WELCOME_SCREEN: {    // 0
                return generateWelcomeButtons(chatId);
            }

            case Config.EXPENSES_SCREEN: {  // 1
                return generateListOfExpencesButtons(chatId);
            }

            case Config.INCOME_SCREEN: { // 2
                return generateListOfIncomeButtons(chatId);
            }

            case Config.INCOME_REVENUE: { // 200
                return Service.getSumm(chatId);
            }

            case Config.INCOME_OTHERREVENUE: { // 210
                return Service.getSumm(chatId);
            }

            default: {
                return new SendMessage().setChatId(chatId).setText("Передайте это число разработчику: " + String.valueOf(Config.screenNumber));
            }
        }
//        return null; //TODO ?!?!???!
    }

    private static SendMessage generateListOfIncomeButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Выручка от продаж");
        inlineKeyboardButton01.setCallbackData("Выручка от продаж");
        inlineKeyboardButton02.setText("Прочие доходы");
        inlineKeyboardButton02.setCallbackData("Прочие доходы");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow02.add(inlineKeyboardButton02);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите категорию доходов.").setReplyMarkup(inlineKeyboardMarkup);
    }

    private static SendMessage generateListOfExpencesButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup1 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton14 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton16 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton17 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton18 = new InlineKeyboardButton();

        inlineKeyboardButton11.setText("На персонал");
        inlineKeyboardButton11.setCallbackData("На персонал");
        inlineKeyboardButton12.setText("Хозяйственные");
        inlineKeyboardButton12.setCallbackData("Хозяйственные");
        inlineKeyboardButton13.setText("Коммуналка");
        inlineKeyboardButton13.setCallbackData("Коммуналка");
        inlineKeyboardButton14.setText("Офис");
        inlineKeyboardButton14.setCallbackData("Офис");
//        inlineKeyboardButton15.setText("Сервис");
//        inlineKeyboardButton15.setCallbackData("Сервис");
        inlineKeyboardButton16.setText("Маркетинг");
        inlineKeyboardButton16.setCallbackData("Маркетинг");
        inlineKeyboardButton17.setText("Налоги");
        inlineKeyboardButton17.setCallbackData("Налоги");
        inlineKeyboardButton18.setText("Прочее");
        inlineKeyboardButton18.setCallbackData("Прочее");

        List<InlineKeyboardButton> keyboardButtonsRow11 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow12 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow13 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow14 = new ArrayList<>();

        keyboardButtonsRow11.add(inlineKeyboardButton11);
        keyboardButtonsRow11.add(inlineKeyboardButton12);

        keyboardButtonsRow12.add(inlineKeyboardButton13);
        keyboardButtonsRow12.add(inlineKeyboardButton14);

//        keyboardButtonsRow13.add(inlineKeyboardButton15);
        keyboardButtonsRow13.add(inlineKeyboardButton16);
        keyboardButtonsRow13.add(inlineKeyboardButton17);

        keyboardButtonsRow14.add(inlineKeyboardButton18);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow11);
        rowList.add(keyboardButtonsRow12);
        rowList.add(keyboardButtonsRow13);
        rowList.add(keyboardButtonsRow14);

        inlineKeyboardMarkup1.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите категорию расходов.").setReplyMarkup(inlineKeyboardMarkup1);
    }

    private static SendMessage generateWelcomeButtons(long chatId) {
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
}
