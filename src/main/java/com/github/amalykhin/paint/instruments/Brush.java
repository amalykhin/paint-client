package com.github.amalykhin.paint.instruments;

import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class Brush extends DrawingInstrument {
    @Override
    public EventType<MouseEvent> apply(Canvas c, MouseEvent me) {
        EventType<MouseEvent> eventType = super.apply(c, me);

        GraphicsContext gc = c.getGraphicsContext2D();
        if (eventType == MouseEvent.MOUSE_DRAGGED) {
            if (!isDrawing)
                return eventType;
            gc.setLineWidth(5);
            gc.strokeLine(x, y, me.getX(), me.getY());
            x = me.getX();
            y = me.getY();
        }

        return eventType;
    }
}
