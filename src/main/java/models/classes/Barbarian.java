package models.classes;

import models.Hero;

public class Barbarian extends Hero {
    public Barbarian() {
        super();
        this.cls = "Barbarian";
        this.hp += 50;
        this.attack -= 5;
        this.defence += 5;
    }
}
