package com.github.amalykhin.paint;

import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

;

public class ColorPalette {
    private Map<Colors, Color> colorMap = new HashMap<>();
    private static ColorPalette instance;

    public static ColorPalette getInstance() {
        if (instance == null)
            instance = new ColorPalette();
        return instance;
    }

    public Color getColor(Colors colorName) {
        return colorMap.get(colorName);
    }

    private ColorPalette() {
        colorMap.put(Colors.BLACK, Color.rgb(0,0,0));
        colorMap.put(Colors.MAROON, Color.web("800000"));
        colorMap.put(Colors.GREEN, Color.web("008000"));
        colorMap.put(Colors.OLIVE, Color.web("808000"));
        colorMap.put(Colors.NAVY, Color.web("000080"));
        colorMap.put(Colors.PURPLE, Color.web("800080"));
        colorMap.put(Colors.TEAL, Color.web("008080"));
        colorMap.put(Colors.SILVER, Color.web("C0C0C0"));
        colorMap.put(Colors.GRAY, Color.web("808080"));
        colorMap.put(Colors.RED, Color.web("FF0000"));
        colorMap.put(Colors.LIME, Color.web("00FF00"));
        colorMap.put(Colors.YELLOW, Color.web("FFFF00"));
        colorMap.put(Colors.BLUE, Color.web("0000FF"));
        colorMap.put(Colors.FUCHSIA, Color.web("FF00FF"));
        colorMap.put(Colors.AQUA, Color.web("00FFFF"));
        colorMap.put(Colors.WHITE, Color.web("FFFFFF"));
    }
}
