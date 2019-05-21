package com.github.amalykhin.paint;

import com.github.amalykhin.paint.instruments.DrawingInstrument;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class CanvasFacade {
    private Canvas mainCanvas;
    private Canvas effectsCanvas;
    private Color foregroundColor;
    private Color backgroundColor;
    private DrawingInstrument instrument;
    private List<ColorChangeObserver> colorChangeObservers = new LinkedList<>();

    private static ColorPalette palette = ColorPalette.getInstance();

    public CanvasFacade(Canvas mainCanvas, Canvas effectsCanvas) {
        this.mainCanvas = mainCanvas;
        this.effectsCanvas = effectsCanvas;
        effectsCanvas.setOnMousePressed(this::handleCanvasMouseEvents);
        effectsCanvas.setOnMouseDragged(this::handleCanvasMouseEvents);
        effectsCanvas.setOnMouseReleased(this::handleCanvasMouseEvents);

        foregroundColor = palette.getColor(Colors.BLACK);
        backgroundColor= palette.getColor(Colors.WHITE);
        GraphicsContext gc = effectsCanvas.getGraphicsContext2D();
        gc.setFill(Color.TRANSPARENT);
        gc.setStroke(palette.getColor(Colors.BLUE));
        gc.setLineDashes(10);
    }

    public void setForegroundColor(Colors colorName) {
        foregroundColor = palette.getColor(colorName);
        mainCanvas.getGraphicsContext2D().setStroke(foregroundColor);
        notifyColorChange(foregroundColor, MouseButton.PRIMARY);
        //instrument.apply(mainCanvas, foregroundColor, backgroundColor);
    }

    public void setBackgroundColor(Colors colorName) {
        backgroundColor = palette.getColor(colorName);
        mainCanvas.getGraphicsContext2D().setFill(backgroundColor);
        notifyColorChange(backgroundColor, MouseButton.SECONDARY);
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setInstrument(DrawingInstrument instrument) {
        this.instrument = instrument;
        //instrument.apply(mainCanvas, foregroundColor, backgroundColor);
        //instrument.applyEffect(effectsCanvas);
    }

    private void clearCanvas(Canvas c) {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, c.getWidth(), c.getHeight());
        gc.setFill(backgroundColor);
    }

    private void handleCanvasMouseEvents(MouseEvent me) {
        if (instrument != null) {
            instrument.apply(mainCanvas, me);
            instrument.applyEffect(effectsCanvas, me);
        }
    }

    private void notifyColorChange(Color color, MouseButton mb) {
        colorChangeObservers.forEach(cco -> cco.colorChanged(color, mb));
    }

    public void clear() {
        GraphicsContext gc = mainCanvas.getGraphicsContext2D();
        gc.setFill(palette.getColor(Colors.WHITE));
        gc.fillRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        gc.setFill(backgroundColor);

        gc = effectsCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, effectsCanvas.getWidth(), effectsCanvas.getHeight());
    }

    public void attachColorChangeObserver(ColorChangeObserver cco) {
        colorChangeObservers.add(cco);
        cco.colorChanged(foregroundColor, MouseButton.PRIMARY);
        cco.colorChanged(backgroundColor, MouseButton.SECONDARY);
    }

    public interface ColorChangeObserver {
        void colorChanged(Color color, MouseButton me);
    }

    public Image getImage() {
        WritableImage image = new WritableImage((int)mainCanvas.getWidth(), (int)mainCanvas.getHeight());
        return mainCanvas.snapshot(null, image);
    }

    public void setImage(Image image) {
        mainCanvas.setWidth(image.getWidth());
        mainCanvas.setHeight(image.getHeight());
        mainCanvas.getGraphicsContext2D().drawImage(image, 0, 0);
    }
}
