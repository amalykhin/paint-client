package com.github.amalykhin.paint;

import com.github.amalykhin.paint.controllers.Controller;
import com.github.amalykhin.paint.controllers.PictureListController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("picture_list.fxml"));

        Parent root = loader.load();
        PictureListController controller = (PictureListController)loader.getController();
        controller.setStage(primaryStage);

        /*
        Controller ctl = loader.getController();
        ColorPalette palette = ColorPalette.getInstance();
        Iterator<Button> colorButtonIterator = Arrays.stream(Colors.values())
            .map(colors -> {
                Button btn = new Button();
                btn.setOnMouseClicked(ctl::handleColorButtonPress);
                btn.setId(colors.toString());
                btn.setMaxWidth(Double.MAX_VALUE);
                Color bgColor = palette.getColor(colors);
                String rgbString = "rgb(" +
                    (int)(bgColor.getRed()*255) + "," +
                    (int)(bgColor.getGreen()*255) + "," +
                    (int)(bgColor.getBlue()*255) + ")";
                btn.setStyle("-fx-background-color: " + rgbString);
                return btn;
            })
            .collect(Collectors.toList())
            .iterator();
        GridPane palettePane = (GridPane)root.lookup("#palette");
        palettePane.setHgap(3);

        for(int i = 1; i < palettePane.getColumnConstraints().size(); i++) {
            for (int j = 0; j < palettePane.getRowConstraints().size(); j++) {
                palettePane.add(colorButtonIterator.next(), i, j);
            }
        }
        */

        primaryStage.setTitle("Paint Client");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
