package com.obrttestbot;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

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
                String tmp = Config.resultString[1];
                Config.resultString[1] = entry.getKey() + tmp;
                break;
            }
        }

        //add subcategory of summ
        if (Config.lastScreen >= 100 | Config.lastScreen == Config.EXPENSES_DIFFERENT) {
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
        if (Config.lastScreen < 100 & Config.lastScreen != Config.EXPENSES_DIFFERENT) {
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

    public static SendMessage cancelEnteringSumm(Update update) {
        resetToDefault();
        return new SendMessage().setChatId(getChatId(update)).setText("Спасибо за сотрудничество.");
    }

    public static void resetToDefault() {
        Config.screenNumber = Config.WELCOME_SCREEN;
        Config.lastScreen = Config.WELCOME_SCREEN;
        Config.enteringSumm = false;
        Config.fillingBudget = false;
        Config.waitingForContragent = false;
        for (int i = 0; i < Config.resultString.length; i++) {
            Config.resultString[i] = " ";
        }
    }

    public static EditMessageText askForSumm(int screenNumber, Update update) {
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
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Введите сумму для категории: " + category)
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update));
        return editMessageText;
    }

    public static SendMessage askForSummAfterDetailsOfExpences(int screenNumber, Update update) {
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
        SendMessage editMessageText = new SendMessage();
        editMessageText.setText("Введите сумму для категории: " + category)
                .setChatId(getChatId(update));
        return editMessageText;
    }

    public static void logToSheets(List<Object> stringsToLog, String sheetName) {
        try {
            Service.writeToSheet(stringsToLog, sheetName);
        } catch (IOException |
                GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static EditMessageText returnTotalSumms(Update update) {
        class Record {
            String date;
            double income;
            double expence;

            public Record(String date, double income, double expence) {
                this.date = date;
                this.income = income;
                this.expence = expence;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public double getIncome() {
                return income;
            }

            public void setIncome(double income) {
                this.income = income;
            }

            public double getExpence() {
                return expence;
            }

            public void setExpence(double expence) {
                this.expence = expence;
            }

            @Override
            public String toString() {
                return "Record{" + "date='" + date + ", income=" + income + ", expence=" + expence + '}';
            }

        }
        Record[] records;
        int numOfRecords;
        List<List<Object>> values = null;
        double income = 0;
        double expences = 0;
        List<Record> listOfRecords = new ArrayList<>();
        try {
            // TODO getting UserID instead of ChatID ?
            //   Config.getSheetNameByUserID((int) chatId),

            String currentDate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

            //getting dates
            values = readTotalSumsFromSheet(Config.SPREADSHEET_URL, "ДДС", "A3", "A");
            numOfRecords = values.size();
            records = new Record[numOfRecords];
            for (int i = 0; i < records.length; i++) {
                records[i] = new Record("", 0, 0);
                listOfRecords.add(new Record("", 0, 0));
            }
            int currentRecord = 0;
            for (List row : values) {
                records[currentRecord].date = String.valueOf(row.get(0));
                Record record = listOfRecords.get(currentRecord);
                listOfRecords.remove(currentRecord);
                record.setDate(String.valueOf(row.get(0)));
                listOfRecords.add(currentRecord, record);
                currentRecord++;
            }
            // getting incomes
            values = readTotalSumsFromSheet(Config.SPREADSHEET_URL, "ДДС", "D3", "D");
            currentRecord = 0;
            for (List row : values) {
                Record record = listOfRecords.get(currentRecord);
                listOfRecords.remove(currentRecord);
                if (!row.get(0).equals(" ")) {
                    income += Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.'));
                    records[currentRecord].income = Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.'));
                    record.setIncome(Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.')));
                } else {
                    record.setIncome(0);
                    records[currentRecord].income = 0;
                }
                listOfRecords.add(currentRecord, record);
                currentRecord++;
            }

            //getting expences
            values = readTotalSumsFromSheet(Config.SPREADSHEET_URL, "ДДС", "E3", "E");
            currentRecord = 0;
            for (List row : values) {
                Record record = listOfRecords.get(currentRecord);
                listOfRecords.remove(currentRecord);
                if (!row.get(0).equals(" ")) {
                    expences += Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.'));
                    records[currentRecord].expence = Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.'));
                    record.setExpence(Double.parseDouble(String.valueOf(row.get(0)).replace(',', '.')));
                } else {
                    record.setExpence(0);
                    records[currentRecord].expence = 0;
                }
                listOfRecords.add(currentRecord, record);
                currentRecord++;
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
        totalText += "\n\n";
        double currIncome = listOfRecords.get(0).getIncome();
        double currExp = listOfRecords.get(0).getExpence();
        String currDate = listOfRecords.get(0).getDate();
        for (int i = 1; i < listOfRecords.size(); i++) {
            Record rec = listOfRecords.get(i);
            if (currDate.equals(rec.getDate())) {
                currExp += rec.getExpence();
                currIncome += rec.getIncome();
            } else {
                totalText += "Date: " + currDate + "\t\t\t";
                totalText += "Total income: " + currIncome + "\t\t\t";
                totalText += "Total expences: " + currExp + "\n";
                currIncome = rec.getIncome();
                currExp = rec.getExpence();
                currDate = rec.getDate();
            }

        }
        totalText += "Date: " + currDate + "\t\t\t";
        totalText += "Total income: " + currIncome + "\t\t\t";
        totalText += "Total expences: " + currExp + "\n";
        resetToDefault();
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText(totalText)
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update));
        return editMessageText;
    }

    public static EditMessageText askForDetailsOfExpences(/*int screenNumber,*/ Update update) {
        Config.enteringDetailsOfExpences = true;
//        String category = "";
        String category = "Другие расходы";
//        for (Map.Entry<String, Integer> entry : Config.buttonsNumbers.entrySet()) {
//            if (entry.getValue().equals(screenNumber)) {
//                category = entry.getKey();
//                break;
//            }
//        }

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("Введите уточнение для категории: " + category)
                .setChatId(getChatId(update))
                .setMessageId(getMessageId(update));
        return editMessageText;
    }

    public static long getChatId(Update update) {
        Long chat_id;
        if (update.hasCallbackQuery()) {
            chat_id = update.getCallbackQuery().getMessage().getChatId();
        } else {
            chat_id = update.getMessage().getChatId();
        }
        return chat_id;
    }

    public static int getMessageId(Update update) {
        Integer message_id;
        if (update.hasCallbackQuery()) {
            message_id = update.getCallbackQuery().getMessage().getMessageId();
        } else {
            message_id = update.getMessage().getMessageId();
        }
        return message_id;
    }
}
