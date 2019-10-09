package com.obrttestbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Date;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage()) {
            long chat_id = message.getChatId();
//            long user_id = user.getId();
            if (message.hasText()) {
                String firstWord;
                if (message.getText().indexOf(" ") > 0) {
                    firstWord = message.getText().substring(0, message.getText().indexOf(" "));
                } else {
                    firstWord = message.getText();
                }
                switch (firstWord) {
                    case "/time@OBRTTestBot":
                    case "/time": {

                        SendMessage messg = new SendMessage()
                                .setChatId(chat_id)
                                .setText(new Date().toString());
                        try {
                            execute(messg);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "/cashflow@OBRTTestBot":
                    case "/cashflow": {
                        try {
                            if (!Config.enteringSumm)
                                execute(Keyboards.sendInlineKeyBoardMessage(chat_id, Config.screenNumber));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "/ident": {
                        System.out.println(update);
                        String toPrint = update.toString()
                                .replace("}", "}\n")
                                .replace("{", "{\n");
                        System.out.println(toPrint);
                        try {
                            SendMessage msg = new SendMessage()
                                    .setChatId(chat_id)
                                    .setText(toPrint);
                            execute(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    default: {
                        //waiting for the summ
                        if (Config.screenNumber == -1 & Config.enteringSumm) {
                            if (!Service.isNumeric(message.getText())) {
                                try {
                                    Config.screenNumber = Config.lastScreen;
                                    Config.enteringSumm = true;
                                    execute(Service.askForSumm(Config.screenNumber, update.getMessage().getChatId()));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Service.prepareResultString(update);
                                Config.waitingForContragent = true;
                                Config.enteringSumm = false;
                                try {
                                    execute(new SendMessage().setChatId(update.getMessage().getChatId()).setText("Укажите контрагента."));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                        //summ entered, waiting for the Contragent
                        if (Config.waitingForContragent) {
                            //add name of author of operation
                            Config.resultString[2] = update.getMessage().getText();
                            Service.logToSheets(Arrays.asList(Config.resultString), "ДДС");
                            jokesAboutSumm(update);
                            try {
                                execute(Service.cancelEnteringSumm(update.getMessage().getChatId()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        //Nothing waiting, just log all conversation
                        if (!Config.fillingBudget) {
                            Service.logToSheets(Service.formatStringsForLog(update), "General");
                        }
                        break;
                    }
                }
            }
        } else if (update.hasCallbackQuery()) { // обработка нажатий на кнопкиs
            try {
                String messageFromTheButton = update.getCallbackQuery().getData();
                Config.screenNumber = Config.buttonsNumbers.get(messageFromTheButton);
                Long currentChatID = update.getCallbackQuery().getMessage().getChatId();
                if (Config.screenNumber < 100 & Config.screenNumber > 0) {
                    execute(Keyboards.sendInlineKeyBoardMessage(currentChatID, Config.screenNumber));
                } else if (Config.screenNumber != Config.EXIT) {
                    execute(Service.askForSumm(Config.screenNumber, currentChatID));
                } else {
                    execute(Service.cancelEnteringSumm(currentChatID));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void jokesAboutSumm(Update update) {
        int incomeSumm = (int) Math.abs(Double.parseDouble(Config.resultString[5].replace(',', '.')));
        String greetText;
        if (Config.lastScreen == Config.INCOME_REVENUE | Config.lastScreen == Config.INCOME_OTHERREVENUE) {
            if (incomeSumm > 10000) {
                greetText = "Отлично сработано. Всегда бы так!";
            } else
                greetText = "Маловато будет...";
        } else {
            if (incomeSumm > 5000) {
                greetText = "Сплошные убытки! Мы так самолёт не купим!";
            } else
                greetText = "К сожалению, иногда, расходы неизбежны";
        }
        SendMessage messg = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(greetText);
        try {
            execute(messg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return Config.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }
}
