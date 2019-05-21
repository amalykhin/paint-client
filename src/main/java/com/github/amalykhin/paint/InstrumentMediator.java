package com.github.amalykhin.paint;

import java.util.HashSet;
import java.util.Set;

public class InstrumentMediator {
    private Set<InstrumentButton> buttons = new HashSet<>();

    void notify(InstrumentButton sender) {
        buttons.add(sender);
        buttons.forEach(button -> {
            if(button.isSelected())
                button.setSelected(false);
        });
        sender.setSelected(true);
    }
}
