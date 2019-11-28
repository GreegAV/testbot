package com.obrttestbot;

import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class BillActions {
    public static void createNewBillSpreadsheet(Spreadsheet spreadsheet, String cellToWrite, String dataToWrite) throws IOException, GeneralSecurityException {
        ValueRange body = new ValueRange()
                .setValues(Arrays.asList(
                        Arrays.asList(dataToWrite)));
        UpdateValuesResponse result = GoogleTools.getSheetsService().spreadsheets().values()
                .update(spreadsheet.getSpreadsheetId(), cellToWrite, body)
                .setValueInputOption("RAW")
                .execute();
    }
}
