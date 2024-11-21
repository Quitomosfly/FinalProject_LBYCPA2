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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        PDFtoCSV();
//        CleanUpCSV();
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
    private void PDFtoCSV() throws IOException {
        String pdfFile = pdfPath.getText();
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
        // SORTING
        System.out.println(text);
    }


    private void CleanUpCSV(){
        // TODO: The raw CSV should be polished. The output will be a better CSV format.
    }


    public static void main(String[] args) {
        launch();
    }
}
