package com.github.amalykhin.paint.instruments;

import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/*
public class Pencil implements DrawingInstrument {
    private boolean isDrawing = false;
    private double x, y;

    @Override
    public void apply(Canvas c, Color fgColor, Color  bgColor) {
        GraphicsContext gc = c.getGraphicsContext2D();

        c.setOnMousePressed(me -> {
            isDrawing = true;
            x = me.getX();
            y = me.getY();
        });
        c.setOnMouseDragged(me -> {
            if (!isDrawing)
                return;
            gc.setStroke(fgColor);
            gc.strokeLine(x, y, me.getX(), me.getY());
            x = me.getX();
            y = me.getY();
        });
        c.setOnMouseReleased(me -> isDrawing = false);
    }
}
*/

public class Pencil extends DrawingInstrument {

    @Override
    public EventType<MouseEvent> apply(Canvas c, MouseEvent me) {
        EventType<MouseEvent> eventType = super.apply(c, me);

        GraphicsContext gc = c.getGraphicsContext2D();
        if (eventType == MouseEvent.MOUSE_DRAGGED) {
            if (!isDrawing)
                return eventType;
            gc.setLineWidth(1);
            gc.strokeLine(x, y, me.getX(), me.getY());
            x = me.getX();
            y = me.getY();
        }

        return eventType;
    }
}
