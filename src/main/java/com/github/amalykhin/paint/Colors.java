package com.github.amalykhin.paint;

public enum Colors {
    BLACK,
    MAROON,
    GREEN,
    OLIVE,
    NAVY,
    PURPLE,
    TEAL,
    SILVER,
    GRAY,
    RED,
    LIME,
    YELLOW,
    BLUE,
    FUCHSIA,
    AQUA,
    WHITE;

    public static Colors get(String colorName) {
        return valueOf(colorName.toUpperCase());
    }
}
