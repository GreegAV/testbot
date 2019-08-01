import Staff.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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

        if (message != null & message.hasText()) {
            logToSheets(update);
            if (message.getText().toLowerCase().equals("/time")) {
                Date currentDate = new Date();
                long chat_id = update.getMessage().getChatId();
                String messageSend = currentDate.toString();
                SendMessage messg = new SendMessage().setChatId(chat_id).setText(messageSend);
                try {
                    execute(messg); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
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

    private void logToSheets(Update update) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        long userID = update.getMessage().getChatId();
        String sheetName = "General";
        String userName = "";
//        if (userID == 221816696) {
//            sheetName = "Test";
//        }
//        if (userID == 148370030) {
//            sheetName = "Батова";
//        }
//        if (userID == 548498472) {
//            sheetName = "Росочинская";
//        }
        if (sheetName.equals("General")) {
            if (update.getMessage().getFrom().getFirstName() != null)
                userName = update.getMessage().getFrom().getFirstName();
            if (update.getMessage().getFrom().getLastName() != null)
                userName += "." + update.getMessage().getFrom().getLastName();
            if (update.getMessage().getFrom().getUserName() != null)
                userName += "(" + update.getMessage().getFrom().getUserName()+")";
        }
        String textFromBot = date + " " + userName + " " + update.getMessage().getText();
        List<String> sentence = Arrays.asList(textFromBot.split(" "));
        System.out.println(sentence.get(3));
        System.out.println(sentence.get(3).startsWith("/"));

        try {
            Service.writeToSheet(Service.getSheetsService(), sentence, sheetName);
        } catch (IOException | GeneralSecurityException e) {
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
