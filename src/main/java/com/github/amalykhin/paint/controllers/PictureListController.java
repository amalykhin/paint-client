package com.github.amalykhin.paint.controllers;

import com.github.amalykhin.paint.PaintApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PictureListController implements Initializable {
    private Stage stage;
    @FXML
    private ListView<String> pictures;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<String> filenames = PaintApi.getPictureList();
            pictures.getItems().addAll(filenames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleNewButtonPress(ActionEvent ae) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    private void handleEditButtonPress(ActionEvent ae) throws Exception {
        String selectedFilename = pictures.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = (Controller)loader.getController();
        controller.setFilename(selectedFilename);
        controller.setImage(PaintApi.getPicture(selectedFilename));
        stage.setScene(new Scene(root));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
