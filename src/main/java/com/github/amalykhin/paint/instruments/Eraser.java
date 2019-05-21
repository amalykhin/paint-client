package com.github.amalykhin.paint.instruments;

import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Eraser extends DrawingInstrument {
    private int size = 10;

    @Override
    public EventType<MouseEvent> apply(Canvas c, MouseEvent me) {
        EventType<MouseEvent> eventType = super.apply(c, me);

        GraphicsContext gc = c.getGraphicsContext2D();
        if (eventType == MouseEvent.MOUSE_DRAGGED) {
            x = me.getX();
            y = me.getY();
            if (!isDrawing)
                return eventType;
            Paint oldFill = gc.getFill();
            gc.setLineWidth(0);
            gc.setFill(Color.WHITE);
            double halfSize = size/2.0;
            gc.fillRect(x-halfSize, y-halfSize, size, size);
            x = me.getX();
            y = me.getY();
            gc.setFill(oldFill);
        }

        return eventType;
    }
}
