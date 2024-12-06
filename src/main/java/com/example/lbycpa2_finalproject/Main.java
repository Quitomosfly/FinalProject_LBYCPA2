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

        cleanedContent.append("Faculty Name,Days,Time\n");

        String line;
        String facultyName = "";

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("\\s+", " ").trim();


            if (line.isEmpty() || line.contains("TOTAL UNITS") || line.contains("FACULTY") || line.contains("SEC") || line.contains("SUBJECT")) {
                continue;
            }


            if (line.contains(",")) {
                String[] parts = line.split(",");
                facultyName = parts[0].trim();
                continue;
            }

            String[] parts = line.split(" ");
            if (parts.length < 5) {
                continue;
            }

            String days = parts[parts.length - 5];
            String time = parts[parts.length - 4];

            days = formatDays(days);
            time = formatTime(time);

            if (days.equals("–") || days.equals("-") || time.equals("–") || time.equals("-")) {
                continue;
            }

            cleanedContent.append(String.format("%s,%s,%s\n", facultyName, days, time));
        }

        reader.close();
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
            case "W":
                return "W";
            case "H":
                return "H";
            case "F":
                return "F";
            default:
                return days;
        }
    }

    private String formatTime(String time) {
        time = time.replace(" – ", "-");

        if (time.contains("-")) {
            return time;
        } else {
            return time + "-" + time;
        }
    }
    public static void main(String[] args) throws IOException {

        Main processor = new Main();
        processor.CleanUpCSV();
        launch();
    }
}
