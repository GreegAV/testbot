package com.obrttestbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



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

        if (update.hasMessage()) {
            long chat_id = update.getMessage().getChatId();
            if (message != null & message.hasText()) {
                String firstWord;
                if (update.getMessage().getText().indexOf(" ") > 0) {
                    firstWord = update.getMessage().getText().substring(0, update.getMessage().getText().indexOf(" "));
                } else {
                    firstWord = update.getMessage().getText();
                }
                switch (firstWord) {
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
                    case "/budget": {
                        Service.logToBudget(update);
                        Service.logToSheets(update);
                        break;
                    }
                    default:
                        try {
                            System.out.println(Config.screenNumber);
                            if (Config.screenNumber > 0)
                                System.out.println("default: " + update.getCallbackQuery().getData());
//                            Config.screenNumber=getNewScreenNumber(update.getCallbackQuery().getData());
                            execute(Keyboards.sendInlineKeyBoardMessage(chat_id, Config.screenNumber));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        Service.logToSheets(update);
                        break;
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                String messageFromTheButton = update.getCallbackQuery().getData();
                Long currentChatID = update.getCallbackQuery().getMessage().getChatId();
                Config.screenNumber= Keyboards.getNewScreenNumber(messageFromTheButton);
//                execute(new SendMessage().setText(
//                        messageFromTheButton)
//                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
                execute(Keyboards.sendInlineKeyBoardMessage(currentChatID, Config.screenNumber));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }




    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("First button"));
        keyboardFirstRow.add(new KeyboardButton("Second button"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

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
