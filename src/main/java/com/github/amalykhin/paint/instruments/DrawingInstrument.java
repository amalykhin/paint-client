package com.github.amalykhin.paint.instruments;

import javafx.event.EventType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/*
public interface DrawingInstrument {
    void apply(Canvas canvas, Color fgColor, Color bgColor);
    default void applyEffect(Canvas c) { }
}
*/

public abstract class DrawingInstrument {
    protected boolean isDrawing = false;
    protected double x, y;

    public EventType<MouseEvent> apply(Canvas canvas, MouseEvent me) {
        EventType<MouseEvent> eventType = (EventType<MouseEvent>)me.getEventType();
        if (eventType == MouseEvent.MOUSE_PRESSED) {
            handleMousePress(me);
        } else if (eventType == MouseEvent.MOUSE_RELEASED) {
            handleMouseRelease(me);
        }

        return eventType;
    }

    public EventType<MouseEvent> applyEffect(Canvas canvas, MouseEvent me) {
        EventType<MouseEvent> eventType = (EventType<MouseEvent>)me.getEventType();
        if (eventType == MouseEvent.MOUSE_RELEASED) {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }

        return eventType;
    }

    private void handleMousePress(MouseEvent me) {
        isDrawing = true;
        x = me.getX();
        y = me.getY();
    }

    private void handleMouseRelease(MouseEvent me) {
        isDrawing = false;
    }
}
