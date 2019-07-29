import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

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
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null & message.hasText()) {
            switch (message.getText().toLowerCase()) {
                case "/help": {
                    showHelpMessage(message);
                    break;
                }
                case "/test": {
                    showTestMessage(message);
                    break;
                }
                case "/time": {
                    Date currentDate = new Date();
                    long chat_id = update.getMessage().getChatId();
                    String messageSend = currentDate.toString();
                    SendMessage messg = new SendMessage().setChatId(chat_id).setText(messageSend);
                    try {
                        execute(messg); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
//////
                case "/ident": {
                    String sendtext = "Ident:\n";
                    sendtext += "Chat ID: " + update.getMessage().getChatId() + "(" + update.getMessage().getFrom().getUserName() + ")";
                    System.out.println(sendtext);
                    sendtext = "Idented.\n";
//                    sendtext+="update.getMessage().getEntities(): "+update.getMessage().getEntities()+"\n\n";
//                    sendtext+="update.getMessage().getFrom(): "+update.getMessage().getFrom()+"\n\n";
//                    sendtext+="update.getMessage(): "+update.getMessage()+"\n\n";
                    sendtext += "update.toString(): " + update.toString() + "\n\n";
                    System.out.println(sendtext);
                    List<String> sentence = Arrays.asList(update.toString().split("\\{"));
                    for (String str : sentence) {
                        System.out.println("{"+str);
                        sendtext += str + "\n\n";
                    }
                    sendMsg(message, sendtext);
                    break;
                }

//////
                default: {
                    System.out.println("Default");
                    sendMsg(message, message.getText());
                    System.out.println(message.getText());
                    break;
                }
            }
        } else {
            System.out.println("message = null | !message.hasText()");
        }
    }

    private void showDefaultMessage(Message message) {
        sendMsg(message, "Default text");
    }

    private void showTestMessage(Message message) {
        sendMsg(message, "Test passed");
    }

    private void showHelpMessage(Message message) {
        sendMsg(message, "Help!");
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
