package com.obrttestbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class Keyboards {

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

            case Config.EXPENSES_PERSONAL_SCREEN: { // 11
                return generateListOfPersonalButtons(chatId);
            }

            case Config.EXPENSES_HOUSEHOLD_SCREEN: { // 12
                return generateListOfHouseholdButtons(chatId);
            }

            case Config.EXPENSES_COMMUNAL_SCREEN: { // 13
                return generateListOfCommunalButtons(chatId);
            }

            case Config.EXPENSES_OFFICE_SCREEN: { // 14
                return generateListOfOfficeButtons(chatId);
            }

            case Config.EXPENSES_MARKETING_SCREEN: { // 16
                return generateListOfMarketingButtons(chatId);
            }

            case Config.EXPENSES_TAXES_SCREEN: { // 17
                return generateListOfTaxesButtons(chatId);
            }

            case Config.EXPENSES_PERSONAL_SALARY:
            case Config.EXPENSES_PERSONAL_STUDY:
            case Config.EXPENSES_HOUSEHOLD_CLEANING:
            case Config.EXPENSES_HOUSEHOLD_REPAIR:
            case Config.EXPENSES_HOUSEHOLD_GOODS:
            case Config.EXPENSES_COMMUNAL_RENT:
            case Config.EXPENSES_COMMUNAL_ELECTRICITY:
            case Config.EXPENSES_COMMUNAL_WATER:
            case Config.EXPENSES_COMMUNAL_SERVICE:
            case Config.EXPENSES_OFFICE_STATIONARY:
            case Config.EXPENSES_OFFICE_CONNECTION:

            case Config.EXPENSES_MARKETING_GOOGLE:
            case Config.EXPENSES_MARKETING_FACEBOOK:
            case Config.EXPENSES_MARKETING_INSTAGRAMM:
            case Config.EXPENSES_MARKETING_CRM:
            case Config.EXPENSES_MARKETING_ENVATO:
            case Config.EXPENSES_MARKETING_TILDA:
            case Config.EXPENSES_MARKETING_OTHER:
            case Config.EXPENSES_TAXES_ESV:
            case Config.EXPENSES_TAXES_NDFL:
            case Config.EXPENSES_TAXES_WAR:
            case Config.EXPENSES_TAXES_EDIN:
            case Config.EXPENSES_TAXES_VAT:
            case Config.EXPENSES_DIFFERENT:
            case Config.INCOME_REVENUE:
            case Config.INCOME_OTHERREVENUE: { // Entering summ
                return Service.getSumm(chatId);
            }

            default: {
                return new SendMessage().setChatId(chatId).setText("Передайте это число разработчику: " + String.valueOf(Config.screenNumber));
            }
        }
    }

    private static SendMessage generateListOfCommunalButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup0 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton04 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Аренда");
        inlineKeyboardButton01.setCallbackData("Аренда");
        inlineKeyboardButton02.setText("Электричество");
        inlineKeyboardButton02.setCallbackData("Электричество");
        inlineKeyboardButton03.setText("Вода");
        inlineKeyboardButton03.setCallbackData("Вода");
        inlineKeyboardButton04.setText("Обслуживание");
        inlineKeyboardButton04.setCallbackData("Обслуживание");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        keyboardButtonsRow02.add(inlineKeyboardButton03);
        keyboardButtonsRow02.add(inlineKeyboardButton04);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);

        inlineKeyboardMarkup0.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите подкатегорию.").setReplyMarkup(inlineKeyboardMarkup0);
    }

    private static SendMessage generateListOfTaxesButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup0 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton04 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton05 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("ЕСВ");
        inlineKeyboardButton01.setCallbackData("ЕСВ");
        inlineKeyboardButton02.setText("НДС");
        inlineKeyboardButton02.setCallbackData("НДС");
        inlineKeyboardButton03.setText("НДФЛ");
        inlineKeyboardButton03.setCallbackData("НДФЛ");
        inlineKeyboardButton04.setText("Военный налог");
        inlineKeyboardButton04.setCallbackData("Военный налог");
        inlineKeyboardButton05.setText("Единый налог");
        inlineKeyboardButton05.setCallbackData("Единый налог");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);
        keyboardButtonsRow01.add(inlineKeyboardButton03);

        keyboardButtonsRow02.add(inlineKeyboardButton04);
        keyboardButtonsRow02.add(inlineKeyboardButton05);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);

        inlineKeyboardMarkup0.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите подкатегорию.").setReplyMarkup(inlineKeyboardMarkup0);
    }

    private static SendMessage generateListOfHouseholdButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup0 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Уборка");
        inlineKeyboardButton01.setCallbackData("Уборка");
        inlineKeyboardButton02.setText("Ремонты");
        inlineKeyboardButton02.setCallbackData("Ремонты");
        inlineKeyboardButton03.setText("Хозтовары");
        inlineKeyboardButton03.setCallbackData("Хозтовары");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        keyboardButtonsRow02.add(inlineKeyboardButton03);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);

        inlineKeyboardMarkup0.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите подкатегорию.").setReplyMarkup(inlineKeyboardMarkup0);
    }

    private static SendMessage generateListOfOfficeButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup0 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Канцтовары");
        inlineKeyboardButton01.setCallbackData("Канцтовары");
        inlineKeyboardButton02.setText("Связь");
        inlineKeyboardButton02.setCallbackData("Связь");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);

        inlineKeyboardMarkup0.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите подкатегорию.").setReplyMarkup(inlineKeyboardMarkup0);
    }

    private static SendMessage generateListOfPersonalButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup0 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Зарплата");
        inlineKeyboardButton01.setCallbackData("Зарплата");
        inlineKeyboardButton02.setText("Обучение");
        inlineKeyboardButton02.setCallbackData("Обучение");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);

        inlineKeyboardMarkup0.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите подкатегорию.").setReplyMarkup(inlineKeyboardMarkup0);
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

    private static SendMessage generateListOfMarketingButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton14 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton16 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton17 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton18 = new InlineKeyboardButton();

        inlineKeyboardButton11.setText("Instagram");
        inlineKeyboardButton11.setCallbackData("Instagram");
        inlineKeyboardButton12.setText("Google");
        inlineKeyboardButton12.setCallbackData("Google");
        inlineKeyboardButton13.setText("Facebook");
        inlineKeyboardButton13.setCallbackData("Facebook");
        inlineKeyboardButton14.setText("CRM");
        inlineKeyboardButton14.setCallbackData("CRM");
        inlineKeyboardButton16.setText("Envato");
        inlineKeyboardButton16.setCallbackData("Envato");
        inlineKeyboardButton17.setText("Tilda");
        inlineKeyboardButton17.setCallbackData("Tilda");
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

        keyboardButtonsRow13.add(inlineKeyboardButton16);
        keyboardButtonsRow13.add(inlineKeyboardButton17);

        keyboardButtonsRow14.add(inlineKeyboardButton18);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow11);
        rowList.add(keyboardButtonsRow12);
        rowList.add(keyboardButtonsRow13);
        rowList.add(keyboardButtonsRow14);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("Выберите подкатегорию.").setReplyMarkup(inlineKeyboardMarkup);
    }

    private static SendMessage generateListOfExpencesButtons(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup1 = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton14 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
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
