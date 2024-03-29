package com.obrttestbot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.obrttestbot.Service.getChatId;
import static com.obrttestbot.Service.getMessageId;

public class Keyboards {

    public static EditMessageText sendInlineKeyBoardMessage(Update update, int screenNumber) {

        switch (screenNumber) {

            case Config.EXPENSES_SCREEN: {  // 1
                return generateListOfExpencesButtons(update);
            }

            case Config.INCOME_SCREEN: { // 2
                return generateListOfIncomeButtons(update);
            }

            case Config.EXPENSES_DIFFERENT: { // 10
                return Service.askForDetailsOfExpences(screenNumber, update);
            }

            case Config.EXPENSES_PERSONAL_SCREEN: { // 11
                return generateListOfPersonalButtons(update);
            }

            case Config.EXPENSES_HOUSEHOLD_SCREEN: { // 12
                return generateListOfHouseholdButtons(update);
            }

            case Config.EXPENSES_COMMUNAL_SCREEN: { // 13
                return generateListOfCommunalButtons(update);
            }

            case Config.EXPENSES_OFFICE_SCREEN: { // 14
                return generateListOfOfficeButtons(update);
            }

            case Config.EXPENSES_MARKETING_SCREEN: { // 16
                return generateListOfMarketingButtons(update);
            }

            case Config.EXPENSES_TAXES_SCREEN: { // 17
                return generateListOfTaxesButtons(update);
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
            case Config.EXPENSES_TAXES_ESV:
            case Config.EXPENSES_TAXES_NDFL:
            case Config.EXPENSES_TAXES_WAR:
            case Config.EXPENSES_TAXES_EDIN:
            case Config.EXPENSES_TAXES_VAT:
            case Config.INCOME_REVENUE:
            case Config.INCOME_OTHERREVENUE: { // Entering contragent
                Config.lastScreen = screenNumber;
                return Service.askForContragent(update);
            }
//            case Config.TOTAL_SCREEN: { // Total expences and profit
//                return Service.returnTotalSumms(update);
//            }

            default: {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setText("Передайте это число разработчику: " + String.valueOf(Config.screenNumber))
                        .setChatId(getChatId(update))
                        .setMessageId(getMessageId(update));
                return editMessageText;
            }
        }
    }

    private static EditMessageText generateListOfCommunalButtons(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton04 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton05 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Аренда");
        inlineKeyboardButton01.setCallbackData("Аренда");
        inlineKeyboardButton02.setText("Электричество");
        inlineKeyboardButton02.setCallbackData("Электричество");
        inlineKeyboardButton03.setText("Вода");
        inlineKeyboardButton03.setCallbackData("Вода");
        inlineKeyboardButton04.setText("Обслуживание");
        inlineKeyboardButton04.setCallbackData("Обслуживание");
        inlineKeyboardButton05.setText("Прекратить ввод");
        inlineKeyboardButton05.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow03 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        keyboardButtonsRow02.add(inlineKeyboardButton03);
        keyboardButtonsRow02.add(inlineKeyboardButton04);

        keyboardButtonsRow03.add(inlineKeyboardButton05);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);
        rowList.add(keyboardButtonsRow03);

        inlineKeyboardMarkup.setKeyboard(rowList);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите подкатегорию.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    private static EditMessageText generateListOfTaxesButtons(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton04 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton05 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton06 = new InlineKeyboardButton();
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
        inlineKeyboardButton06.setText("Прекратить ввод");
        inlineKeyboardButton06.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow03 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);
        keyboardButtonsRow01.add(inlineKeyboardButton03);

        keyboardButtonsRow02.add(inlineKeyboardButton04);
        keyboardButtonsRow02.add(inlineKeyboardButton05);

        keyboardButtonsRow03.add(inlineKeyboardButton06);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);
        rowList.add(keyboardButtonsRow03);

        inlineKeyboardMarkup.setKeyboard(rowList);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите подкатегорию.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    private static EditMessageText generateListOfHouseholdButtons(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton04 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Уборка");
        inlineKeyboardButton01.setCallbackData("Уборка");
        inlineKeyboardButton02.setText("Ремонты");
        inlineKeyboardButton02.setCallbackData("Ремонты");
        inlineKeyboardButton03.setText("Хозтовары");
        inlineKeyboardButton03.setCallbackData("Хозтовары");
        inlineKeyboardButton04.setText("Прекратить ввод");
        inlineKeyboardButton04.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow03 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        keyboardButtonsRow02.add(inlineKeyboardButton03);

        keyboardButtonsRow03.add(inlineKeyboardButton04);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);
        rowList.add(keyboardButtonsRow03);

        inlineKeyboardMarkup.setKeyboard(rowList);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите подкатегорию.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    private static EditMessageText generateListOfOfficeButtons(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Канцтовары");
        inlineKeyboardButton01.setCallbackData("Канцтовары");
        inlineKeyboardButton02.setText("Связь");
        inlineKeyboardButton02.setCallbackData("Связь");
        inlineKeyboardButton03.setText("Прекратить ввод");
        inlineKeyboardButton03.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow03 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        keyboardButtonsRow02.add(inlineKeyboardButton03);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);
        rowList.add(keyboardButtonsRow03);

        inlineKeyboardMarkup.setKeyboard(rowList);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите подкатегорию.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    private static EditMessageText generateListOfPersonalButtons(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Зарплата");
        inlineKeyboardButton01.setCallbackData("Зарплата");
        inlineKeyboardButton02.setText("Обучение");
        inlineKeyboardButton02.setCallbackData("Обучение");
        inlineKeyboardButton03.setText("Прекратить ввод");
        inlineKeyboardButton03.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);

        keyboardButtonsRow02.add(inlineKeyboardButton03);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);

        inlineKeyboardMarkup.setKeyboard(rowList);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите подкатегорию.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;
    }

    private static EditMessageText generateListOfIncomeButtons(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Выручка от продаж");
        inlineKeyboardButton01.setCallbackData("Выручка от продаж");
        inlineKeyboardButton02.setText("Прочие доходы");
        inlineKeyboardButton02.setCallbackData("Прочие доходы");
        inlineKeyboardButton03.setText("Прекратить ввод");
        inlineKeyboardButton03.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow03 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow02.add(inlineKeyboardButton02);
        keyboardButtonsRow03.add(inlineKeyboardButton03);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);
        rowList.add(keyboardButtonsRow03);

        inlineKeyboardMarkup.setKeyboard(rowList);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите категорию расходов.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    private static EditMessageText generateListOfMarketingButtons(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton14 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton16 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton17 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton18 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton999 = new InlineKeyboardButton();

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
//        inlineKeyboardButton18.setText("Прочее");
//        inlineKeyboardButton18.setCallbackData("Прочее");
        inlineKeyboardButton999.setText("Прекратить ввод");
        inlineKeyboardButton999.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow11 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow12 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow13 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow14 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow15 = new ArrayList<>();

        keyboardButtonsRow11.add(inlineKeyboardButton11);
        keyboardButtonsRow11.add(inlineKeyboardButton12);

        keyboardButtonsRow12.add(inlineKeyboardButton13);
        keyboardButtonsRow12.add(inlineKeyboardButton14);

        keyboardButtonsRow13.add(inlineKeyboardButton16);
        keyboardButtonsRow13.add(inlineKeyboardButton17);

//        keyboardButtonsRow14.add(inlineKeyboardButton18);
        keyboardButtonsRow15.add(inlineKeyboardButton999);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow11);
        rowList.add(keyboardButtonsRow12);
        rowList.add(keyboardButtonsRow13);
        rowList.add(keyboardButtonsRow14);
        rowList.add(keyboardButtonsRow15);

        inlineKeyboardMarkup.setKeyboard(rowList);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите подкатегорию.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    private static EditMessageText generateListOfExpencesButtons(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton14 = new InlineKeyboardButton();
//        InlineKeyboardButton inlineKeyboardButton15 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton16 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton17 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton18 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton999 = new InlineKeyboardButton();

        inlineKeyboardButton11.setText("Персонал");
        inlineKeyboardButton11.setCallbackData("Персонал");
        inlineKeyboardButton12.setText("Хозяйственные");
        inlineKeyboardButton12.setCallbackData("Хозяйственные");
        inlineKeyboardButton13.setText("Коммунальные");
        inlineKeyboardButton13.setCallbackData("Коммунальные");
        inlineKeyboardButton14.setText("Офисные");
        inlineKeyboardButton14.setCallbackData("Офисные");
//        inlineKeyboardButton15.setText("Сервис");
//        inlineKeyboardButton15.setCallbackData("Сервис");
        inlineKeyboardButton16.setText("Маркетинговые");
        inlineKeyboardButton16.setCallbackData("Маркетинговые");
        inlineKeyboardButton17.setText("Налоги");
        inlineKeyboardButton17.setCallbackData("Налоги");
        inlineKeyboardButton18.setText("Другие расходы");
        inlineKeyboardButton18.setCallbackData("Другие расходы");
        inlineKeyboardButton999.setText("Прекратить ввод");
        inlineKeyboardButton999.setCallbackData("Прекратить ввод");

        List<InlineKeyboardButton> keyboardButtonsRow11 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow12 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow13 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow14 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow15 = new ArrayList<>();

        keyboardButtonsRow11.add(inlineKeyboardButton11);
        keyboardButtonsRow11.add(inlineKeyboardButton12);

        keyboardButtonsRow12.add(inlineKeyboardButton13);
        keyboardButtonsRow12.add(inlineKeyboardButton14);

//        keyboardButtonsRow13.add(inlineKeyboardButton15);
        keyboardButtonsRow13.add(inlineKeyboardButton16);
        keyboardButtonsRow13.add(inlineKeyboardButton17);

        keyboardButtonsRow14.add(inlineKeyboardButton18);

        keyboardButtonsRow15.add(inlineKeyboardButton999);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow11);
        rowList.add(keyboardButtonsRow12);
        rowList.add(keyboardButtonsRow13);
        rowList.add(keyboardButtonsRow14);
        rowList.add(keyboardButtonsRow15);

        inlineKeyboardMarkup.setKeyboard(rowList);

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Выберите категорию расходов.")
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return editMessageText;
    }

    public static SendMessage generateWelcomeButtons(Update update) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton inlineKeyboardButton01 = new InlineKeyboardButton();
        inlineKeyboardButton01.setText("Расходы");
        inlineKeyboardButton01.setCallbackData("Расходы");

        InlineKeyboardButton inlineKeyboardButton02 = new InlineKeyboardButton();
        inlineKeyboardButton02.setText("Доходы");
        inlineKeyboardButton02.setCallbackData("Доходы");

        InlineKeyboardButton inlineKeyboardButton03 = new InlineKeyboardButton();
        inlineKeyboardButton03.setText("Прекратить ввод");
        inlineKeyboardButton03.setCallbackData("Прекратить ввод");

        //        InlineKeyboardButton inlineKeyboardButton04 = new InlineKeyboardButton();
//        inlineKeyboardButton04.setText("Итого баланс");
//        inlineKeyboardButton04.setCallbackData("Итого баланс");

        List<InlineKeyboardButton> keyboardButtonsRow01 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow02 = new ArrayList<>();

        keyboardButtonsRow01.add(inlineKeyboardButton01);
        keyboardButtonsRow01.add(inlineKeyboardButton02);
//        keyboardButtonsRow02.add(inlineKeyboardButton04);
        keyboardButtonsRow02.add(inlineKeyboardButton03);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow01);
        rowList.add(keyboardButtonsRow02);

        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(update.getMessage().getText())
                .setChatId(getChatId(update))
                .setReplyMarkup(inlineKeyboardMarkup);

        return sendMessage;
    }
}
