package com.obrttestbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Date;


public class Bot extends TelegramLongPollingBot {
    // По правильному, это надо было сделать отдельной веткой в гитхабе, но так сложилось :(

//    public void onUpdateReceived(Update update) {
//        update.getUpdateId();
//        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
//
//        String msgToBot = update.getMessage().getText();
//        System.out.println(msgToBot);
//
//        if (msgToBot.equals("Hi!")) {
//            sendMessage.setText("И тебе Хай!");
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//
////        if (msgToBot.equals("@Счет".toLowerCase()) || msgToBot.equals("@Счёт".toLowerCase())) {
////            sendMessage.setText("Кому счет?");
////            try {
////                execute(sendMessage);
////            } catch (TelegramApiException e) {
////                e.printStackTrace();
////            }
////        }
//
//        if (msgToBot.equals("@ident")) {
//            String sendtext = "Ident:\n";
//            sendtext += "Chat ID: " + update.getMessage().getChatId();
//            sendtext += "\n";
//            String getFirstName = update.getMessage().getFrom().getFirstName();
//            String getLastName = update.getMessage().getFrom().getLastName();
//            String getUserName = update.getMessage().getFrom().getUserName();
//            if (getFirstName != null) {
//                sendtext += "update.getMessage().getFrom().getFirstName(): " + getFirstName;
//                sendtext += "\n";
//            }
//            if (getUserName != null) {
//                sendtext += "update.getMessage().getFrom().getLastName(): " + getLastName;
//                sendtext += "\n";
//            }
//            if (getUserName != null) {
//                sendtext += "update.getMessage().getFrom().getUserName(): " + getUserName;
//                sendtext += "\n";
//            }
////            System.out.println(sendtext);
//            sendtext = "Idented.";
//            sendMessage.setText(sendtext);
//            System.out.println(update.getMessage());
//            try {
//                execute(sendMessage);
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//        ///////////////////// Finally log to sheets
//        logToGeneral(update);
//
//    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (update.hasMessage()) {
            long chat_id = update.getMessage().getChatId();
            long user_id = update.getMessage().getFrom().getId();
            if (message != null && message.hasText()) {
                String firstWord;
                if (update.getMessage().getText().indexOf(" ") > 0) {
                    firstWord = update.getMessage().getText().substring(0, update.getMessage().getText().indexOf(" "));
                } else {
                    firstWord = update.getMessage().getText();
                }
                switch (firstWord) {
                    case "/time@OBRTTestBot":
                    case "/time": {

                        SendMessage messg = new SendMessage()
                                .setChatId(chat_id)
                                .setText(new Date().toString());
                        try {
                            execute(messg); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "/budget@OBRTTestBot":
                    case "/budget": {
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
                        try {
                            SendMessage msg = new SendMessage()
                                    .setChatId(chat_id)
                                    .setText(update.toString());
                            execute(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "/start":
                    case "/start@OBRTTestBot": {
                        System.out.println(update);
                        try {
                            SendMessage msg = new SendMessage()
                                    .setChatId(chat_id)
                                    .setText("Добро пожаловать!");
                            execute(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    default: {
                        if (Config.screenNumber == -1 & Config.enteringSumm) {
                            if (!Service.isNumeric(update.getMessage().getText())) {
                                try {
                                    Config.screenNumber = Config.lastScreen;
                                    Config.enteringSumm = true;
                                    execute(Service.askForSumm(Config.screenNumber, update.getMessage().getChatId()));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Service.logToDDS(update);
                                jokesAboutSumm(update);
                                try {
                                    execute(Service.cancelEnteringSumm(update.getMessage().getChatId()));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
//                        if (update.getMessage().getText().indexOf(" ") > 0)
                        if (!Config.fillingBudget) {
                            Service.logToGeneral(update);
                        } else {
                            try {
                                Config.screenNumber = Config.lastScreen;
                                execute(Service.askForSumm(Config.lastScreen, update.getMessage().getChatId()));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                }
            }
        } else if (update.hasCallbackQuery()) { // обработка нажатий на кнопки
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
        double incomeSumm = Double.parseDouble(update.getMessage().getText().replace(',', '.'));
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
