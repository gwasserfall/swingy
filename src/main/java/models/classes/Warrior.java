package models.classes;

import models.Hero;

public class Warrior extends Hero {
    public Warrior() {
        super();
        this.cls = "Warrior";
        this.defence += 5;
        this.attack += 5;
        this.hp += 10;
    }
}
