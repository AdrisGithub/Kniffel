package de.nuernberger.kniffel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KniffelApplication extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage){
        stage.setResizable(false);
        try{
            Image dice = new Image(new FileInputStream("src/main/resources/de/nuernberger/kniffel/dice/dice0.png"));
            choosePlayer(stage,dice);
        }catch(IOException io){
            throw new RuntimeException("Dice Images not found");
        }
    }
    public void choosePlayer (Stage stage, Image dice) {
        ChoiceBox choiceAmount = new ChoiceBox(FXCollections.observableArrayList("2","3","4","5","6","7","8","9","10"));
        Button btnSetAmount = new Button("LET'S KNIFFEL !");
        Button btnInstructions = new Button("Instructions");
        Label lblPlayers = new Label("players");
        VBox layout = new VBox();
        setStyleOfAmountPlayerWindow(btnInstructions,btnSetAmount,lblPlayers,choiceAmount,layout);
        layout.getChildren().addAll(choiceAmount,btnSetAmount,lblPlayers,btnInstructions);
        Scene firstScene = new Scene(layout, 400,200);
        firstScene.setFill(Color.ANTIQUEWHITE);
        stage.getIcons().add(dice);
        stage.setScene(firstScene);
        stage.setTitle("Kniffel");
        stage.show();
        btnSetAmount.setOnAction(event -> {
            if (choiceAmount.getValue()!=null) {startGame(stage, Integer.parseInt(choiceAmount.getValue().toString()),dice);}
            else {start(stage);}});
        btnInstructions.setOnAction(event -> instructionScene(stage));
    }

    private void instructionScene(Stage stage){
        VBox vBox = new VBox();
        String rules;
        try {
            byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/de/nuernberger/kniffel/rules.txt"));
            rules = new String (bytes);
        }catch (IOException io){
            throw new RuntimeException("Rules.txt wasn't found");
        }
        Label lblInstructions = new Label(rules);
        Button goBack = new Button("Go Back to Menu");
        lblInstructions.setFont(Font.font("Verdana",FontWeight.MEDIUM, 15.0));
        lblInstructions.setTextAlignment(TextAlignment.CENTER);
        lblInstructions.setTranslateX(40);
        goBack.setStyle("-fx-background-color: #5ca08e");
        goBack.setTranslateX(320);
        goBack.setTranslateY(20);
        goBack.setFont(Font.font("Verdana",FontWeight.MEDIUM, FontPosture.ITALIC, 15.0));
        goBack.setTextAlignment(TextAlignment.CENTER);
        goBack.setWrapText(true);
        goBack.setOnAction(event -> start(stage));
        Scene instructions = new Scene(vBox,800,400);
        vBox.getChildren().addAll(lblInstructions,goBack);
        stage.setScene(instructions);
    }
    public void setStyleOfAmountPlayerWindow(Button goToInstructions,Button btnSetAmount, Label lblPlayers,ChoiceBox choiceAmount,VBox layout){
        choiceAmount.setTranslateX(100);
        choiceAmount.setTranslateY(30);
        choiceAmount.setStyle("-fx-border-color: #5ca08e");
        lblPlayers.setTranslateX(155);
        lblPlayers.setTranslateY(-22);
        lblPlayers.setFont(Font.font("Verdana",FontWeight.MEDIUM, FontPosture.ITALIC, 15.0));
        btnSetAmount.setStyle("-fx-background-color: #5ca08e");
        btnSetAmount.setTranslateX(100);
        btnSetAmount.setTranslateY(70);
        btnSetAmount.setFont(Font.font("Verdana",FontWeight.MEDIUM, FontPosture.ITALIC, 15.0));
        btnSetAmount.setTextAlignment(TextAlignment.CENTER);
        btnSetAmount.setWrapText(true);
        goToInstructions.setStyle("-fx-background-color: #5ca08e");
        goToInstructions.setTranslateX(120);
        goToInstructions.setTranslateY(70);
        goToInstructions.setFont(Font.font("Verdana",FontWeight.MEDIUM, FontPosture.ITALIC, 15.0));
        goToInstructions.setTextAlignment(TextAlignment.CENTER);
        goToInstructions.setWrapText(true);
        layout.setPadding(new Insets(20,20,20,20));
    }
    public void startGame(Stage stage, int amountPlayers, Image dice){
        KniffelPort.initPlayers(amountPlayers);
        Scene scene;
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:/"+toAbsolutePath("src/main/resources/de/nuernberger/kniffel/kniffel-scb.fxml")));
            scene = new Scene(fxmlLoader.load(), 780, 530);
            scene.getStylesheets().add(new URL("file:/"+toAbsolutePath("src/main/resources/de/nuernberger/kniffel/Kniffel.css")).toString());
        }catch (IOException io){
            throw new RuntimeException("Fxml or Css wasn't found");
        }
        stage.setTitle("Kniffel");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setY(50);
        stage.setX(250);
        stage.getIcons().add(dice);
        stage.show();
    }
    public static String toAbsolutePath(String maybeRelative) {
        Path path = Paths.get(maybeRelative);
        if (!path.isAbsolute()) {
            Path base = Paths.get("");
            path = base.resolve(path).toAbsolutePath();
        }
        return path.normalize().toString();
    }

}