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

    public static void readFromSheet(String sheetName, String sheetTab, String startCell, String targetRange)
            throws IOException, GeneralSecurityException {
        sheetsService = GoogleTools.getSheetsService();

        String range = sheetTab + "!" + startCell + ":" + targetRange;
        ValueRange response = sheetsService.spreadsheets().values()
                .get(sheetName, range)
                .execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
//            System.out.println(values.get(1).get(0));
//            System.out.println(values.get(1).get(2));
//            System.out.println(values.get(2));
            for (List row : values) {
                if (row.get(0).toString().substring(0, 2).equals("16"))
                    System.out.println(row.get(0));
            }
        }
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
//        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");

//        try {
//            Date date = dateFormat.parse(
//                    String.valueOf(readResult.getValueRanges().get(0).getValues().get(0).get(0)) +
//                            " " +
//                            String.valueOf(readResult.getValueRanges().get(1).getValues().get(0).get(0)));
//            System.out.println("Data&Time: " + date);
//
////            System.out.println("Time: " + time);
//            System.out.println("Read date: " + readResult.getValueRanges().get(0).getValues().get(0).get(0));
//            System.out.println("Read time: " + readResult.getValueRanges().get(1).getValues().get(0).get(0));
//            System.out.println("Formatted date: " + dateFormat.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        for (int i = 0; i < readResult.getValueRanges().size(); i++) {
//            if (readResult.getValueRanges().get(i).getValues() != null) {
//                System.out.print(readResult.getValueRanges().get(i).getValues().get(0).get(0));
//                System.out.print("\t");
//            }
//        }
        System.out.println("\nReading finished");
    }

    public static List<List<Object>> readTotalSumsFromSheet(String sheetName, String sheetTab, String startCell, String targetRange)
            throws IOException, GeneralSecurityException {
        sheetsService = GoogleTools.getSheetsService();

        String range = sheetTab + "!" + startCell + ":" + targetRange;
        ValueRange response = sheetsService.spreadsheets().values()
                .get(sheetName, range)
                .execute();
        return response.getValues();
    }

    static List<Object> formatStringsForLog(Update update) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));
        String name = formatUserName(update);
        String text = update.getMessage().getText();

        return Arrays.asList((date + " " + name + " " + text).split(" "));

    }

    static void prepareResultString(Update update) {
        //   0      1           2          3       4      5        6          7
        // Дата	Расшифровка	Контрагент	Приход	Расход	Всего	Вид ДДС	Статья ДДС

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(new Date(update.getMessage().getDate() * 1000L));

        //add date of operation
        Config.resultString[0] = date;

        //add category of summ
        for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
            if (entry.getValue().equals(Config.lastScreen)) {
                Config.resultString[1] = entry.getKey();
                break;
            }
        }

        //add subcategory of summ
        if (Config.lastScreen >= 100) {
            for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
                if (entry.getValue().equals(Config.lastScreen / 10)) {
                    Config.resultString[7] = entry.getKey();
                    break;
                }
            }
        } else {
            for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
                if (entry.getValue().equals(Config.lastScreen)) {
                    Config.resultString[7] = entry.getKey();
                    break;
                }
            }
        }

        // cutting out the summ
        String inputSumm = update.getMessage().getText().replace(',', '.');
        inputSumm = inputSumm.replace(" ", "");
        double incomeSumm = Math.abs(Double.parseDouble(inputSumm));

        // add type of summ
        if (Config.lastScreen < 100) {
            Config.resultString[3] = String.valueOf(incomeSumm);
            Config.resultString[5] = String.valueOf(incomeSumm);
            Config.resultString[6] = "1. Доход";
        } else {
            Config.resultString[4] = String.valueOf(incomeSumm);
            Config.resultString[5] = String.valueOf((-1.0) * incomeSumm);
            Config.resultString[6] = "2. Расход";
        }
    }

    static boolean isNumeric(String str) {
        String tmp = str.replace(',', '.');
        tmp = tmp.replace(" ", "");

        if (tmp.indexOf('.') != tmp.lastIndexOf('.'))
            return false;
        return tmp.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
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

    public static SendMessage cancelEnteringSumm(long chatId) {
        Config.screenNumber = Config.WELCOME_SCREEN;
        Config.lastScreen = Config.WELCOME_SCREEN;
        Config.enteringSumm = false;
        Config.fillingBudget = false;
        Config.waitingForContragent = false;
        for (int i = 0; i < Config.resultString.length; i++) {
            Config.resultString[i] = " ";
        }
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

    public static void logToSheets(List<Object> stringsToLog, String sheetName) {
        try {
            Service.writeToSheet(stringsToLog, sheetName);
        } catch (IOException |
                GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    public static SendMessage returnTotalSumms(long chatId) {

        List<List<Object>> values = null;
        double income = 0;
        double expences = 0;

        try {
            // TODO getting UserID instead of ChatID ?
            //   Config.getSheetNameByUserID((int) chatId),
            values = readTotalSumsFromSheet(Config.SPREADSHEET_URL, "ДДС", "D3", "D");
            for (List row : values) {
                if (!row.get(0).equals(" "))
                    income += Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.'));
            }

            values = readTotalSumsFromSheet(Config.SPREADSHEET_URL, "ДДС", "E3", "E");
            for (List row : values) {
                if (!row.get(0).equals(" "))
                    expences += Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.'));
            }

        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        String totalText = "Суммарно расход: ";
        totalText += expences;
        totalText += "\nСуммарно доход: ";
        totalText += income;
        totalText += "\nИтого баланс: ";
        totalText += income - expences;
        return new SendMessage().setChatId(chatId).setText(totalText);
    }
}
