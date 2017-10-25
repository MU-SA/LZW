package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.text.html.ListView;
import java.awt.*;
import java.io.File;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("LZ77");
        primaryStage.setScene(new Scene(root, 800, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
