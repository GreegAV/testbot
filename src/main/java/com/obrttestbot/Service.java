package com.obrttestbot;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Service {
    private static Sheets sheetsService;

    private static void writeToSheet(List<Object> sentence, String sheetName) throws IOException, GeneralSecurityException {
        sheetsService = GoogleTools.getSheetsService();

        ValueRange appendBody = new ValueRange()
                .setValues(Arrays.asList(new List[]{sentence}));

        AppendValuesResponse appendResult = sheetsService.spreadsheets().values()
                .append(Config.SPREADSHEET_URL, sheetName, appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();
    }

    public static void readFromSheet(Sheets sheetsService, String sheetName, String startOfTheRange, String endOfTheRange) throws IOException {
        String range = sheetName + "!" + startOfTheRange + ":" + endOfTheRange;

        ValueRange response = sheetsService.spreadsheets().values()
                .get(Config.SPREADSHEET_URL, range)
                .execute();

        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println(values.get(0).size());
            for (List<Object> row : values) {
                System.out.printf("%s, %s, %s\n", row.get(0), row.get(1), row.get(2));
            }
        }
    }

    static void logToSheets(Update update) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        long userID = update.getMessage().getChatId();
        String sheetName = "General";
        String userName = "";
        String textFromBot = update.getMessage().getText();
        boolean isCommand = textFromBot.charAt(0) == '/';
        if (isCommand) {
            sheetName = "Test";
            String command = update.getMessage().getText().substring(0, update.getMessage().getText().indexOf(" "));
            textFromBot = trimFirstWordFromMessage(textFromBot);
        } else {
            textFromBot = date + " " + formatUserName(update) + " " + textFromBot;
        }

        List<Object> sentence = Arrays.asList(textFromBot.split(" "));

        try {
            Service.writeToSheet(sentence, sheetName);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    static void logToBudget(Update update) {
        String sheetName = "Расход";

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        // Prepare string for logging into budget
        StringBuilder textToLog = new StringBuilder();

        //add date of operation
        textToLog.append(date);
        textToLog.append(" ");

        //add name of author of operation
        textToLog.append(formatUserName(update));
        textToLog.append(" ");

        //trim the command /budget from the logstring
        String sourceMessage = trimFirstWordFromMessage(update.getMessage().getText());

        // cutting out the summ
        String sumString = sourceMessage.substring(0, sourceMessage.indexOf(" "));
        sumString = sumString.replace('.', ',').trim();

        double summa = Double.parseDouble(sumString.replace(',', '.'));
        if (summa >= 0) {
            sheetName = "Приход"; //by default - Расход
        } else {
            sumString=sumString.replace('-',' ').trim();
        }
        textToLog.append(sumString);
        textToLog.append(" ");

        //trim the summ from the logstring and log the rest of the message
        textToLog.append(trimFirstWordFromMessage(sourceMessage));

        List<Object> sentence = Arrays.asList(textToLog.toString().split(" "));

        try {
            Service.writeToSheet(sentence, sheetName);
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

}
