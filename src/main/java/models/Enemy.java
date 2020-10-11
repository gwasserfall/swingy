package models;

public class Enemy {

    public Integer attack;
    public Integer defence;
    public Integer hp;

    public Enemy(Hero player) {
        this.attack = player.level + 20;
        this.defence = 10 + player.attack / 4;
        this.hp = 100;
    }
}
