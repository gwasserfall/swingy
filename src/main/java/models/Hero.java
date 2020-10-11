package models;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;

public class Hero implements Serializable {

    @Size(min=3,max=20, message = "Name must be between 3 and 20 characters")
    public String name;
    public String cls;
    public Integer level;
    public Integer exp;
    public Integer attack;
    public Integer defence;
    public Integer x;
    public Integer y;
    public Integer hp;
    public ArrayList<String> attackLog = new ArrayList<String>();
    public Integer expGained;

    public Hero() {

        this.level = 1;
        this.x = 0;
        this.y = 0;
        this.exp = 0;
        this.attack = 30;
        this.defence = 10;
        this.hp = 100;
    }
}
