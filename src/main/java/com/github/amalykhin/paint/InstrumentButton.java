package com.github.amalykhin.paint;

import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;

public class InstrumentButton extends ToggleButton {
    private InstrumentMediator mediator;

    public InstrumentButton() {}

    public void setMediator(InstrumentMediator mediator) {
        this.mediator = mediator;
        addEventHandler(ActionEvent.ACTION, me -> this.mediator.notify(this));
    }
}
