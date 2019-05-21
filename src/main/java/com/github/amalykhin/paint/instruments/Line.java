package com.github.amalykhin.paint.instruments;

import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class Line extends DrawingInstrument {
    @Override
    public EventType<MouseEvent> apply(Canvas c, MouseEvent me) {
        GraphicsContext gc = c.getGraphicsContext2D();
        EventType<MouseEvent> eventType = (EventType<MouseEvent>)me.getEventType();
        if (eventType == MouseEvent.MOUSE_RELEASED) {
            if (!isDrawing)
                return eventType;
            gc.setLineWidth(1);
            gc.strokeLine(x, y, me.getX(), me.getY());
        }
        super.apply(c, me);

        return eventType;
    }

    @Override
    public EventType<MouseEvent> applyEffect(Canvas c, MouseEvent me) {
        EventType<MouseEvent> eventType = super.applyEffect(c, me);
        GraphicsContext gc = c.getGraphicsContext2D();
        if (eventType == MouseEvent.MOUSE_DRAGGED) {
            gc.clearRect(0, 0, c.getWidth(), c.getHeight());
            if (!isDrawing)
                return eventType;
            gc.setLineWidth(1);
            gc.strokeLine(x, y, me.getX(), me.getY());
        }

        return eventType;
    }
}
