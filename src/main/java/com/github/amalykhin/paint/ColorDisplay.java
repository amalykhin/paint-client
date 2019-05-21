package com.github.amalykhin.paint;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorDisplay extends Region implements CanvasFacade.ColorChangeObserver {
    private Rectangle fgColor, bgColor;
    private int rectangleSize = 30;

    public ColorDisplay() {
        fgColor = new Rectangle(0, 0, rectangleSize, rectangleSize);
        bgColor = new Rectangle(rectangleSize/2.0, rectangleSize/2.0, rectangleSize, rectangleSize);
        getChildren().addAll(bgColor, fgColor);
    }

    @Override
    public void colorChanged(Color color, MouseButton me) {
        switch(me) {
            case PRIMARY:
                fgColor.setFill(color);
                break;
            case SECONDARY:
                bgColor.setFill(color);
        }
    }
}
