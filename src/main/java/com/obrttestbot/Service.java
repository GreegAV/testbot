package com.obrttestbot;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Service {
    private static Sheets sheetsService;

    private static void writeToSheet(List<Object> sentence, String sheetName) throws IOException, GeneralSecurityException {
        sheetsService = GoogleTools.getSheetsService();

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(sentence));

        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(Config.SPREADSHEET_URL, sheetName, appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    static void logToGeneral(Update update) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        long userID = update.getMessage().getChatId();
//        String sheetName = "General";
//        String userName = "";
        String textFromBot = update.getMessage().getText();
//        boolean isCommand = textFromBot.charAt(0) == '/';
//        if (isCommand) {
//            sheetName = "Test";
//            String command = update.getMessage().getText().substring(0, update.getMessage().getText().indexOf(" "));
//            textFromBot = trimFirstWordFromMessage(textFromBot);
//        } else {
        textFromBot = date + " " + formatUserName(update) + " " + textFromBot;
//        }

        List<Object> sentence = Arrays.asList(textFromBot.split(" "));

        try {
            Service.writeToSheet(sentence, "General");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    static void logToDDS(Update update) {
        //   0      1           2          3       4      5        6          7
        // Дата	Расшифровка	Контрагент	Приход	Расход	Всего	Вид ДДС	Статья ДДС
        String[] resultString = new String[8];
        for (int i = 0; i < 8; i++) {
            resultString[i] = " ";
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        //add date of operation
        resultString[0] = date;

        //add category of summ
        for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
            if (entry.getValue().equals(Config.lastScreen)) {
                resultString[1] = entry.getKey();
                break;
            }
        }

        //add subcategory of summ
        if (Config.lastScreen >= 100) {
            for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
                if (entry.getValue().equals(Config.lastScreen / 10)) {
                    resultString[7] = entry.getKey();
                    break;
                }
            }
        } else {
            for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
                if (entry.getValue().equals(Config.lastScreen)) {
                    resultString[7] = entry.getKey();
                    break;
                }
            }
        }

        //add name of author of operation
        resultString[2] = formatUserName(update);

        // cutting out the summ
        String inputSumm = update.getMessage().getText().replace(',', '.');
        inputSumm = inputSumm.replace(" ", "");
        double incomeSumm = Math.abs(Double.parseDouble(inputSumm));

        // add type of summ
        if (Config.lastScreen < 100) {
            resultString[3] = String.valueOf(incomeSumm);
            resultString[5] = String.valueOf(incomeSumm);
            resultString[6] = "1. Доход";
        } else {
            resultString[4] = String.valueOf(incomeSumm);
            resultString[5] = String.valueOf((-1.0) * incomeSumm);
            resultString[6] = "2. Расход";
        }

        try {
            Service.writeToSheet(Arrays.asList(resultString), "ДДС");
        } catch (IOException |
                GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    static boolean isNumeric(String str) {
        String tmp = str.replace(',', '.');
        tmp = tmp.replace(" ", "");

        if (tmp.indexOf('.') != tmp.lastIndexOf('.'))
            return false;
        return tmp.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    static void logToBudget(Update update) {
        //   0      1           2          3       4      5        6          7
        // Дата	Расшифровка	Контрагент	Приход	Расход	Всего	Вид ДДС	Статья ДДС
//        String[] resultString = new String[8];
//        for (int i = 0; i < 8; i++) {
//            resultString[i] = " ";
//        }

        String sheetName = "Расход";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        // Prepare string for logging into budget
        StringBuilder textToLog = new StringBuilder();

        //add date of operation
//        resultString[0] = date;
        textToLog.append(date);
        textToLog.append(" ");

        //add name of author of operation
        String kontragent = formatUserName(update);
//        resultString[2] = kontragent;
        textToLog.append(kontragent);
        textToLog.append(" ");

        //trim the command /budget from the logstring
        String sourceMessage = trimFirstWordFromMessage(update.getMessage().getText());

        // cutting out the summ
        String sumString = sourceMessage.substring(0, sourceMessage.indexOf(" "));
        sumString = sumString.replace('.', ',').trim();

        double summa = Double.parseDouble(sumString.replace(',', '.'));
        if (summa >= 0) {
            sheetName = "Приход"; //by default - Расход
//            resultString[3] = sumString;
//            resultString[4] = " ";
//            resultString[5] = sumString;
//            resultString[6] = "1. Доход";
        } else {
            sumString = sumString.replace('-', ' ').trim();
//            resultString[3] = " ";
//            resultString[4] = sumString;
//            resultString[5] = sumString;
//            resultString[6] = "2. Расход";
        }
        textToLog.append(sumString);
        textToLog.append(" ");

        //trim the summ from the logstring and log the rest of the message
        String restString = trimFirstWordFromMessage(sourceMessage);
        textToLog.append(restString);
//        resultString[1] = restString;

        List<Object> sentence = Arrays.asList(textToLog.toString().split(" "));

        try {
            Service.writeToSheet(sentence, sheetName);
//            Service.writeToSheet(Arrays.asList(resultString), "ДДС");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    private static String formatUserName(Update update) {
        StringBuilder stringBuilder = new StringBuilder();
        if (update.getMessage().getFrom().getFirstName() != null) {
            stringBuilder.append(update.getMessage().getFrom().getFirstName());
        }
        if (update.getMessage().getFrom().getLastName() != null) {
            stringBuilder.append(".");
            stringBuilder.append(update.getMessage().getFrom().getLastName());
        }
        if (update.getMessage().getFrom().getUserName() != null) {
            stringBuilder.append("(");
            stringBuilder.append(update.getMessage().getFrom().getUserName());
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    private static String trimFirstWordFromMessage(String string4Trim) {
        return string4Trim.substring(string4Trim.indexOf(" ") + 1);
    }

    public static SendMessage cancelEnteringSumm(long chatId) {
        Config.screenNumber = Config.WELCOME_SCREEN;
        Config.lastScreen = Config.WELCOME_SCREEN;
        Config.enteringSumm = false;
        Config.fillingBudget = false;
        return new SendMessage().setChatId(chatId).setText("Спасибо за сотрудничество.");
    }

    public static SendMessage askForSumm(int screenNumber, long chatId) {
        Config.screenNumber = -1;
        Config.lastScreen = screenNumber;
        Config.enteringSumm = true;
        Config.fillingBudget = true;
        String category = "";
        for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
            if (entry.getValue().equals(screenNumber)) {
                category = entry.getKey();
                break;
            }
        }
        return new SendMessage().setChatId(chatId).setText("Введите сумму для категории: " + category);
    }

}
