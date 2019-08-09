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

    public static void writeToSheet(List<String> sentence, String sheetName) throws IOException, GeneralSecurityException {
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

    public static void logToSheets(Update update) {
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
            textFromBot = cutCommandFromMessage(update);
        } else {
            textFromBot = date + " " + formatUserName(update) + " " + textFromBot;
        }

        List<String> sentence = Arrays.asList(textFromBot.split(" "));

        try {
            Service.writeToSheet(sentence, sheetName);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    private static String formatUserName(Update update) {
        StringBuffer stringBuffer = new StringBuffer();
        if (update.getMessage().getFrom().getFirstName() != null) {
            stringBuffer.append(update.getMessage().getFrom().getFirstName());
        }
        if (update.getMessage().getFrom().getLastName() != null) {
            stringBuffer.append(".");
            stringBuffer.append(update.getMessage().getFrom().getLastName());
        }
        if (update.getMessage().getFrom().getUserName() != null) {
            stringBuffer.append("(");
            stringBuffer.append(update.getMessage().getFrom().getUserName());
            stringBuffer.append(")");
        }
        return stringBuffer.toString();
    }

    private static String cutCommandFromMessage(Update update) {
        return update.getMessage().getText().substring(update.getMessage().getText().indexOf(" ") + 1);
    }

}
