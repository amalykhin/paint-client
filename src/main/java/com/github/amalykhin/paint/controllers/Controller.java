package com.github.amalykhin.paint.controllers;

import com.github.amalykhin.paint.*;
import com.github.amalykhin.paint.instruments.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    private Map<String, DrawingInstrument> drawingInstruments = initInstruments();
    private CanvasFacade canvas;
    private String filename;

    @FXML
    private Canvas mainCanvas;
    @FXML
    private Canvas effectsCanvas;
    @FXML
    private ColorDisplay colorDisplay;
    @FXML
    private GridPane instruments;
    @FXML
    private GridPane palette;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        effectsCanvas.heightProperty().bindBidirectional(mainCanvas.heightProperty());
        effectsCanvas.widthProperty().bindBidirectional(mainCanvas.widthProperty());
        canvas = new CanvasFacade(mainCanvas, effectsCanvas);
        canvas.clear();
        canvas.attachColorChangeObserver(colorDisplay);
        InstrumentMediator mediator = new InstrumentMediator();
        instruments.getChildren()
            .forEach(node-> ((InstrumentButton)node).setMediator(mediator));
        /*
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        */
        ColorPalette palette = ColorPalette.getInstance();
        Iterator<Button> colorButtonIterator = Arrays.stream(Colors.values())
            .map(colors -> {
                Button btn = new Button();
                btn.setOnMouseClicked(this::handleColorButtonPress);
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
        this.palette.setHgap(3);

        for(int i = 1; i < this.palette.getColumnConstraints().size(); i++) {
            for (int j = 0; j < this.palette.getRowConstraints().size(); j++) {
                this.palette.add(colorButtonIterator.next(), i, j);
            }
        }
    }

    @FXML
    public void handleInstrumentButtonPress(ActionEvent ae) {
        ButtonBase targetButton = (ButtonBase)ae.getSource();
        canvas.setInstrument(drawingInstruments.get(targetButton.getId()));
    }

    @FXML
    public void handleColorButtonPress(MouseEvent me) {
        Button colorButton = (Button)me.getSource();
        Colors colorName = Colors.get(colorButton.getId());
        if(me.getButton() == MouseButton.PRIMARY) {
            canvas.setForegroundColor(colorName);
        } else if (me.getButton() == MouseButton.SECONDARY) {
            canvas.setBackgroundColor(colorName);
        }
    }

    private Map<String, DrawingInstrument> initInstruments() {
        Map<String, DrawingInstrument> result = new HashMap<>();

        result.put("pencil", new Pencil());
        result.put("brush", new Brush());
        result.put("line", new Line());
        result.put("eraser", new Eraser());
        result.put("rectangle", new Rectangle());
        result.put("ellipse", new Ellipse());

        return result;
    }

    @FXML
    private void handleSave(ActionEvent ae) throws Exception {
        if (filename == null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Save");
            dialog.setContentText("Filename");

            Optional<String> result = dialog.showAndWait();
            if (!result.isPresent()) {
                return;
            }
            filename = result.get();
        }

        BufferedImage img = SwingFXUtils.fromFXImage(canvas.getImage(), null);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "png", os);


        try {
            PaintApi.postPicture(filename, os.toByteArray());
        } catch (ConnectException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection Error");
            alert.setContentText("Unable to connect to server. Try again later");
            alert.showAndWait();
        }
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setImage(Image image) {
        canvas.setImage(image);
    }
}
