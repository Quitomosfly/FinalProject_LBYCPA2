package com.example.lbycpa2_finalproject;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
//Test test test
public class Main extends Application {
    @FXML
    private Button button;
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
        CleanUpCSV();
        Stage currentStage = (Stage) button.getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-page.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Main");
        stage.show();
    }


    //One of the parameter should be PDF
    @FXML
    private void PDFtoCSV(){
        // TODO: Convert PDF into csv. It should output a CSV File.
    }


    private void CleanUpCSV(){
        // TODO: The raw CSV should be polished. The output will be a better CSV format.
    }


    public static void main(String[] args) {
        launch();
    }
}