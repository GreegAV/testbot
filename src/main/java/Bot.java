import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    //    private long chat_id;
//    String lastMessage = "";
//    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    //    public String getMessage(String msg){
//        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
//        KeyboardRow keyboardFirstRow = new KeyboardRow();
//        KeyboardRow keyboardSecondRow = new KeyboardRow();
//
//        replyKeyboardMarkup.setSelective(true);
//        replyKeyboardMarkup.setResizeKeyboard(true);
//        replyKeyboardMarkup.setOneTimeKeyboard(false);
//
//        if (msg.equals())
//
//        return "Выбрать...";
//    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null & message.hasText()) {
            switch (message.getText().toLowerCase()) {
                case "/help": {
                    sendMsg(message, "Help!");
                    break;
                }
                case "/test": {
                    sendMsg(message, "Test passed");
                    break;
                }

                default: {
                    sendMsg(message, "Default text");
                    break;
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

    @Override
    public String getBotUsername() {
        return Config.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return Config.BOT_TOKEN;
    }
}
