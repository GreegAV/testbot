package com.obrttestbot;

import org.telegram.telegrambots.meta.api.objects.Update;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Service {
    private static Sheets sheetsService;

    private static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = Service.class.getResourceAsStream("/client_secrets.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(), new InputStreamReader(in)
        );

        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(),
                clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(
                flow, new LocalServerReceiver())
                .authorize("user");
    }

    public static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
    }

    public static void writeToSheet(List<String> sentence, String sheetName) throws IOException, GeneralSecurityException {
        sheetsService = Service.getSheetsService();

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
//        if (userID == 221816696) {
//            sheetName = "Test";
//        }
//        if (userID == 148370030) {
//            sheetName = "Батова";
//        }
//        if (userID == 548498472) {
//            sheetName = "Росочинская";
//        }
        String textFromBot = update.getMessage().getText();
        boolean command = textFromBot.charAt(0) == '/';
        if (command) {
            sheetName = "Test";
            textFromBot = cutCommandFromMessage(update);
        } else {
            if (sheetName.equals("General")) {
                if (update.getMessage().getFrom().getFirstName() != null) {
                    userName = update.getMessage().getFrom().getFirstName();
                }
                if (update.getMessage().getFrom().getLastName() != null) {
                    userName += "." + update.getMessage().getFrom().getLastName();
                }
                if (update.getMessage().getFrom().getUserName() != null) {
                    userName += "(" + update.getMessage().getFrom().getUserName() + ")";
                }
                textFromBot = date + " " + userName + " " + textFromBot;
            }

        }

        List<String> sentence = Arrays.asList(textFromBot.split(" "));

        try {
            Service.writeToSheet(sentence, sheetName);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static String cutCommandFromMessage(Update update) {
        //todo implement this

        return update.getMessage().getText().substring(update.getMessage().getText().indexOf(" ") + 1);
    }

}
