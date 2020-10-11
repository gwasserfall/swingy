package models.classes;

import models.Hero;

public class Mage extends Hero {
    public Mage() {
        super();
        this.cls = "Mage";
        this.attack += 10;
        this.defence -= 5;
    }
}
