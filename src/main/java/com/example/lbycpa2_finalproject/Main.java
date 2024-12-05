package com.example.lbycpa2_finalproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

//Test test test
public class Main extends Application {
    @FXML
    private Button button;
    @FXML
    private TextField pdfPath;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void MainPage() throws IOException {
 //          PDFtoCSV();
        CleanUpCSV();
//        Stage currentStage = (Stage) button.getScene().getWindow();
//        currentStage.close();
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
//        Stage stage = new Stage();
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setScene(scene);
//        stage.setTitle("Main");
//        stage.show();
    }


    //One of the parameter should be PDF
    @FXML
    private void PDFtoCSV() throws IOException {    // COMMIT TEST 1
        String pdfFile = pdfPath.getText(); // Commit test 2
        System.out.println(pdfFile);
        // TODO: Convert PDF into csv. It should output a CSV File.
        File file = new File(pdfFile);
        FileInputStream fis = new FileInputStream(file);    // change pathname according to your machine
        PDDocument doc =  PDDocument.load(fis);

        PDFTextStripper reader = new PDFTextStripper(); // reader
        String text = reader.getText(doc);

        // SORTING
        String[] surnames = new String[100];    // array of surnames
        int count = 0;
        String[] lines = text.split("\n");    // split text into lines
        for (int i = 0; i < lines.length; i++) {
            String[] words = lines[i].split(" ");   // split lines into words
            if (words[0].contains(",")) {   // if first word is a surname (contains comma)
                System.out.println(words[0]);
                surnames[count] = words[0]; // get length of words array, if less than: no middle name
                System.out.println("LENGTH: " + words.length);
            }
        }
        // WRITING
        File unorganized_csv = new File("src/main/resources/unorganized_csv.txt");
        if (unorganized_csv.createNewFile()) {
            System.out.println("File created: " + unorganized_csv.getName());
        }
        FileWriter writer = new FileWriter("src/main/resources/unorganized_csv.txt");
        writer.write(text);
        writer.close();
    }


    private void CleanUpCSV() throws IOException {

        File rawCsvFile = new File("src/main/resources/unorganized_csv.txt");
        BufferedReader reader = new BufferedReader(new FileReader(rawCsvFile));
        StringBuilder cleanedContent = new StringBuilder();

        // Write the headers first
        cleanedContent.append("Faculty Name,Days,Time\n");

        String line;
        String facultyName = "";
        String currentFacultyLastName = "";

        // Iterate through each line of the raw data
        while ((line = reader.readLine()) != null) {
            // Clean up excessive spaces and remove leading/trailing spaces
            line = line.replaceAll("\\s+", " ").trim();

            // Skip empty lines
            if (line.isEmpty()) {
                continue;
            }

            // Skip "TOTAL UNITS" line
            if (line.contains("TOTAL UNITS")) {
                continue;
            }

            // Handle "FACULTY NAME" or faculty name rows (only last and first name)
            if (line.contains(",")) {
                // Split by comma to capture Faculty Name (First and Last)
                String[] parts = line.split(",");

                // Handle faculty name
                if (parts.length >= 2) {
                    currentFacultyLastName = parts[0].trim();  // Last Name
                    facultyName = currentFacultyLastName + " " + parts[1].trim(); // First Name
                }
                continue; // Skip this row, as it's part of the faculty name
            }

            // Skip faculty-specific rows that are not related to Days or Time (e.g., "SUBJECT", "FACULTY", etc.)
            if (line.contains("FACULTY") || line.contains("LEAVE") || line.contains("SUBJECT")) {
                continue;
            }

            // Process Days and Time rows (should contain at least 2 parts: Days, Time)
            String[] parts = line.split(" "); // Split by spaces
            if (parts.length < 2) {
                continue; // Skip rows that do not contain at least two parts
            }

            // Extract days and time
            String days = parts[0];  // First part is Days
            String time = parts[1];  // Second part is Time

            // Format days and time for consistency
            days = formatDays(days);  // Normalize days (e.g., "M", "T", "TF")
            time = formatTime(time);  // Normalize time (e.g., "1300-1400")

            // Write the cleaned row
            cleanedContent.append(String.format("\"%s\",\"%s\",\"%s\"\n", facultyName, days, time));
        }

        reader.close();

        // Write cleaned data to a new CSV file
        File cleanedCsvFile = new File("src/main/resources/organized_csv.txt");
        if (!cleanedCsvFile.exists()) {
            cleanedCsvFile.createNewFile();
        }

        FileWriter writer = new FileWriter(cleanedCsvFile);
        writer.write(cleanedContent.toString());
        writer.close();
    }
    private String formatDays(String days) {
        switch (days) {
            case "M":
                return "M";
            case "T":
                return "T";
            case "F":
                return "F";
            case "TF":
                return "T, F";
            case "MH":
                return "M, H";  // Assuming H is for Thursday
            default:
                return days;  // Return as is if not in predefined options
        }
    }

    private String formatTime(String time) {
        if (time.contains("-")) {
            return time; // If time already contains range, return as-is
        } else {
            return time + "-" + time; // If no range, assume same start and end time
        }
    }
    public static void main(String[] args) throws IOException {

        Main processor = new Main();
        processor.CleanUpCSV();
        launch();
    }
}
