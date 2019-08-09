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
//        logToSheets(update);
//
//    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null & message.hasText()) {
            String firstWord = update.getMessage().getText().substring(0, update.getMessage().getText().indexOf(" "));
            switch (firstWord) {
                case "/time": {
                    long chat_id = update.getMessage().getChatId();
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
                case "/budget": {
                    Service.logToBudget(update);
                    break;
                }
                default:
                    Service.logToSheets(update);
                    break;
            }
//            if (update.getMessage().getText().equals("/time")) {
//                long chat_id = update.getMessage().getChatId();
//                SendMessage messg = new SendMessage()
//                        .setChatId(chat_id)
//                        .setText(new Date().toString());
//                try {
//                    execute(messg); // Sending our message object to user
//                } catch (TelegramApiException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Service.logToSheets(update);
//            }
        } else {
            if (!message.hasText()) {
                //TODO обработка стикеров, фоточек и т.д.
            }
        }
    }

    private void sendMsg(Message message, String default_text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(default_text);

        try {
            execute(sendMessage);
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
