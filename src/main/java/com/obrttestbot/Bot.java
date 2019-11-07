package com.obrttestbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.obrttestbot.Service.getChatId;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage()) {
            if (message.hasDocument()) {
                System.out.println("getFileName " + message.getDocument().toString());
//                if (message.getDocument().getMimeType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
//                    System.out.println("Excell!!");
                System.out.println(message.getDocument());
                if (message.getDocument().getMimeType().equals("text/plain")) {
                    //TODO save uploaded file
                    //You can use getFile(file_id).
                    // This function returns a File object containing file_path.
                    // You can download the file through this address:
                    //
                    //https://api.telegram.org/file/bot<token>/<file_path>
                    String fullFileName = "https://api.telegram.org/file/bot" +
                            getBotToken() + "/" + message.getDocument().getFileId();
                }
            }


            if (message.hasAnimation()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает соообщения в виде анимации."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasAudio()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает аудиосообщения."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasVoice()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает аудиосообщения."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasPhoto()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает фото."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasDocument()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает пока документы."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasVideo()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает видео."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasVideoNote()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не принимает видео."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            if (message.hasSticker()) {
                try {
                    execute(new SendMessage()
                            .setChatId(getChatId(update))
                            .setText("Бот не понимает стикеры."));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

            if (message.hasText()) {
                String firstWord;
                if (message.getText().indexOf(" ") > 0) {
                    firstWord = message.getText().substring(0, message.getText().indexOf(" "));
                } else {
                    firstWord = message.getText();
                }
                switch (firstWord) {
                    case "/start@OBRTTestBot":
                    case "/start": {
                        SendMessage messg = new SendMessage()
                                .setChatId(getChatId(update))
                                .setText("Welcome. Когда-то тут будет инструкция.");
                        try {
                            execute(messg);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "/time@OBRTTestBot":
                    case "/time": {
                        SendMessage messg = new SendMessage()
                                .setChatId(getChatId(update))
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
                        Service.resetToDefault();
                        try {
//                            if (!Config.enteringSumm) {
//                                execute(Keyboards.sendInlineKeyBoardMessage(update, Config.WELCOME_SCREEN));
                            execute(Keyboards.generateWelcomeButtons(update));
//                            }
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    case "/ident":
                    case "/ident@OBRTTestBot": {
                        String[] ident = {
                                String.valueOf(update.getMessage().getFrom().getId()),
                                update.getMessage().getFrom().getUserName() == null ? " " : update.getMessage().getFrom().getUserName(),
                                update.getMessage().getFrom().getFirstName() == null ? " " : update.getMessage().getFrom().getFirstName(),
                                update.getMessage().getFrom().getLastName() == null ? " " : update.getMessage().getFrom().getLastName(),
                                update.getMessage().getChat().isGroupChat() ? update.getMessage().getChat().getTitle() : "Private chat"
                        };

                        Service.logToSheets(Arrays.asList(ident), "Ident");
                        try {
                            SendMessage msg = new SendMessage()
                                    .setChatId(getChatId(update))
                                    .setText("Idented");
                            execute(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }

                    default: {
                        //waiting for the summ
                        if (Config.enteringSumm) {
                            if (!Service.isNumeric(message.getText())) {
                                try {
                                    execute(Service.askForSumm(update));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Config.enteringSumm = false;
                                Service.prepareResultString(update);
                                Service.logToSheets(Arrays.asList(Config.resultString), "ДДС");
                                try {
                                    execute(Service.cancelEnteringSumm(update));
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        }
                        //waiting for the contragent
                        if (Config.waitingForContragent) {
                            Config.waitingForContragent = false;
                            Config.resultString[2] = update.getMessage().getText();
                            try {
                                execute(Service.askForSumm(update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        // waiting for details of expences
                        if (Config.enteringDetailsOfExpences) {
                            Config.enteringDetailsOfExpences = false;
                            Config.resultString[1] += "(" + update.getMessage().getText() + ")";
                            try {
                                execute(Service.askForSumm(update));
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        //Nothing waiting, just log all conversation
                        if (!Config.fillingBudget | !Config.enteringSumm | !Config.waitingForContragent) {
                            List<Object> listForLogging = Service.formatStringsForLog(update);
                            Service.logToSheets(listForLogging, "General");
//                            String sheetNameByUserID = Config.getSheetNameByUserID(update.getMessage().getFrom().getId());
//                            Service.logToSheets(listForLogging, sheetNameByUserID);
                        }
                        break;
                    }
                }
            }
        } else if (update.hasCallbackQuery()) { // обработка нажатий на кнопки
            try {
                String messageFromTheButton = update.getCallbackQuery().getData();
                Config.screenNumber = Config.buttonsNumbers.get(messageFromTheButton);
//                if (Config.screenNumber < 100 & Config.screenNumber > 0) {
//                    execute(Keyboards.sendInlineKeyBoardMessage(update, Config.screenNumber));
//                } else if (Config.screenNumber != Config.EXIT) {
//                    execute(Service.askForContragent(update));
//                } else {
//                    execute(Service.cancelEnteringSumm(update));
//                }
                if (Config.screenNumber != Config.EXIT) {
                    execute(Keyboards.sendInlineKeyBoardMessage(update, Config.screenNumber));
                } else {
                    execute(Service.cancelEnteringSumm(update));
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    private void jokesAboutSumm(Update update) {
        int summForJokes = (int) Math.abs(Double.parseDouble(Config.resultString[5].replace(',', '.')));
        String jokeText;
        if (Config.lastScreen == Config.INCOME_REVENUE | Config.lastScreen == Config.INCOME_OTHERREVENUE) {
            if (summForJokes > 10000)
                jokeText = "Отлично сработано. Всегда бы так!";
            else
                jokeText = "Маловато будет...";
        } else {
            if (summForJokes > 5000)
                jokeText = "Сплошные убытки! Мы так самолёт не купим!";
            else
                jokeText = "К сожалению, иногда, расходы неизбежны";
        }
        SendMessage messg = new SendMessage()
                .setChatId(getChatId(update))
                .setText(jokeText);
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
}
