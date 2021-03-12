package br.com.bearbot.commands;

import java.awt.*;

public enum RankEnum {

    Plastic(0, "**Plastic**", new Color(0, 0, 0)),
    Wood(1000, "**Wood**", new Color(148, 0, 211)),
    Glass(5000, "**Glass**", new Color(75, 0, 130)),
    Aluminium(10000, "**Aluminium**", new Color(0, 0, 255)),
    Steel(15000, "**Steel**", new Color(0, 255, 0)),
    Gold(20000, "**Gold**", new Color(255, 127, 0)),
    Diamond(50000, "**Diamond**", new Color(255, 0, 0)),

    Pokebolas(100000, "Pokebolas", new Color(0, 0, 0)),
    Battle(150000, "Battle", new Color(148, 0, 211)),
    Pikachu(200000, "Pikachu", new Color(255, 255, 0)),
    Bulbassur(250000, "Bulbassur", new Color(56, 176, 222)),
    Charmander(500000, "Charmander", new Color(255, 165, 0)),
    Sandshrew(800000, "Sandshrew", new Color(219, 219, 112)),
    Onix(900000, "Onix", new Color(79, 79, 47)),
    Cube(1000000, "Cube", new Color(207, 181, 59)),
    Ensignia(25000000, " Ensignia", new Color(255, 0, 0));

    private long xp;
    private String name;
    private Color color;

    private RankEnum(long xp, String name, Color color) {
        this.name = name;
        this.color = color;
        this.xp = xp;
    }

    public long getXp() {
        return xp;
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

}
