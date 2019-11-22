package com.obrttestbot;

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
import com.google.api.services.sheets.v4.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoogleTools {
    public static Credential authorize() throws IOException, GeneralSecurityException {
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
        Credential credential = GoogleTools.authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
    }

    public static Spreadsheet newSpreadSheet(String sheetName) throws IOException, GeneralSecurityException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties().setTitle(sheetName));
        return getSheetsService()
                .spreadsheets()
                .create(spreadSheet).execute();
    }

    public static void deleteSpreadSheet(String spreadSheetId) throws IOException, GeneralSecurityException {
        BatchUpdateSpreadsheetRequest batchUpdateSpreadsheetRequest = new
                BatchUpdateSpreadsheetRequest();

        Sheets sheetsService = getSheetsService();
        List<Request> requests = new ArrayList<>();
        DeleteSheetRequest deleteSheetRequest = new DeleteSheetRequest().setSheetId(0);

        requests.add(new Request().setDeleteSheet(deleteSheetRequest));

        batchUpdateSpreadsheetRequest.setRequests(requests);
        Sheets.Spreadsheets.BatchUpdate request =
                sheetsService.spreadsheets().batchUpdate(spreadSheetId, batchUpdateSpreadsheetRequest);

        request.execute();
    }

    public static void copySpreadSheet(String sourceSpreadSheetId, int sourceSheetId, String destinationSpreadsheetId) throws IOException, GeneralSecurityException {
        CopySheetToAnotherSpreadsheetRequest requestBody = new CopySheetToAnotherSpreadsheetRequest();
        requestBody.setDestinationSpreadsheetId(destinationSpreadsheetId);

        Sheets sheetsService = getSheetsService();
        Sheets.Spreadsheets.SheetsOperations.CopyTo request =
                sheetsService.spreadsheets().sheets().copyTo(sourceSpreadSheetId, sourceSheetId, requestBody);

        SheetProperties response = request.execute();

        deleteSpreadSheet(destinationSpreadsheetId);

    }
}
