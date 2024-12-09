package com.example.lbycpa2_finalproject;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


//Test test test
public class Main extends Application {
    @FXML
    private Button button;
    @FXML
    private TextField pdfPath;
    @FXML
    private VBox dragTarget;
    @FXML
    private Label label;
    @FXML
    private Label dropped;
    @FXML
    AnchorPane anchorPane;
    String filePath;
    static final int scheduleLength = 65;

    public Main() throws IOException {
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Home Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        setupDragAndDrop();
    }

    private void setupDragAndDrop() {
        // Ensure the dragTarget handles drag events
        dragTarget.setOnDragOver(event -> {
            if (event.getGestureSource() != dragTarget && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        dragTarget.setOnDragDropped(this::handleFileDrop);
    }

    private void handleFileDrop(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if (dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0); // Get the first dropped file
            filePath = file.getAbsolutePath(); // Get its absolute path

            // Update the label to show the file path
            dropped.setText("Filepath: " + filePath);

            // For debugging: Print file path to console
            System.out.println("Filepath: " + filePath);

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void MainPage() throws IOException {
//        PDFtoCSV(filePath);
//        CleanUpCSV();
        // Load the panelist data from the CSV file
        String[][] panelists = csvToStringArray("src/main/resources/organized_csv.txt");
        if (panelists == null || panelists.length == 0) {
            System.out.println("Error: No panelist data found in the CSV file.");
            return;
        }

        // Extract unique panelist names
        Set<String> uniqueNamesSet = new HashSet<>();
        for (int i = 1; i < panelists.length; i++) {
            uniqueNamesSet.add(panelists[i][0].trim());
        }
        String[] arrayOfPanelists = uniqueNamesSet.toArray(new String[0]);

        // Load the main page FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Parent root = fxmlLoader.load();

        // Get the controller for the FXML and set up the panelist menu
        VisualDataCommonTime controller = fxmlLoader.getController();
        controller.setPanelistsName(arrayOfPanelists);
        controller.setPanelists(panelists);
        // Display the main page in a new stage
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.setTitle("Main Page");
        stage.show();
    }


    private String[][] csvToStringArray(String csvFilePath){
        int i = 0;
        String[][] data = new String[0][];

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String thisLine;
            while ((thisLine = reader.readLine()) != null) {
                ++i;

                String[][] newdata = new String[i][3];

                String[] strar = thisLine.split(",");
                newdata[i - 1] = strar;

                if (i > 1) {
                    System.arraycopy(data, 0, newdata, 0, i - 1);
                }
                data = newdata;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //One of the parameter should be PDF
    @FXML
    private void PDFtoCSV(String pdfPath) throws IOException {    // COMMIT TEST 1
        String pdfFile = pdfPath; // Commit test 2
        System.out.println(pdfFile);

        File file = new File(pdfFile);

        try (PDDocument doc = PDDocument.load(file)) { // Use try-with-resources to ensure doc is closed
            PDFTextStripper reader = new PDFTextStripper(); // reader
            String text = reader.getText(doc);

            // SORTING
            String[] surnames = new String[100]; // array of surnames
            int count = 0;
            String[] lines = text.split("\n"); // split text into lines
            for (String line : lines) {
                String[] words = line.split(" "); // split lines into words
                if (words[0].contains(",")) { // if first word is a surname (contains comma)
                    System.out.println(words[0]);
                    surnames[count++] = words[0]; // get length of words array, if less than: no middle name
                    System.out.println("LENGTH: " + words.length);
                }
            }

            // WRITING
            File unorganizedCsv = new File("src/main/resources/unorganized_csv.txt");
            if (unorganizedCsv.createNewFile()) {
                System.out.println("File created: " + unorganizedCsv.getName());
            }
            try (FileWriter writer = new FileWriter(unorganizedCsv)) {
                writer.write(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            case "S":
                return "S";
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
        launch();
    }
}
