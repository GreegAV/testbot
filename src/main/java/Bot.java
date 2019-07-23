import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

                default: {
                    showDefaultMessage(message);
                    break;
                }
            }
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
