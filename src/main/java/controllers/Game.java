package controllers;

import models.*;
import models.classes.*;
import swingy.Swingy;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    public static Boolean MovePlayerUpAndCheckForEncounter() {
        MovePlayer(0, -1);
        return CheckForEncounter();
    }

    public static Boolean MovePlayerRightAndCheckForEncounter() {
        MovePlayer(1, 0);
        return CheckForEncounter();
    }

    public static Boolean MovePlayerDownAndCheckForEncounter() {
        MovePlayer(0, 1);
        return CheckForEncounter();
    }

    public static Boolean MovePlayerLeftAndCheckForEncounter() {
        MovePlayer(-1, 0);
        return CheckForEncounter();
    }

    private static void MovePlayer(int x, int y) {
        Swingy.state.player.x += x;
        Swingy.state.player.y += y;
    }

    private static Boolean CheckForEncounter() {
        return (ThreadLocalRandom.current().nextInt(0, 5) == 0);
    }

    private static Integer RandomInt(int min, int max)
    {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static Boolean TryEscape() {

        return (ThreadLocalRandom.current().nextInt(0, 101) >= 50);
    }

    public static Boolean Fight() {

        Hero player = Swingy.state.player;
        Enemy enemy = new Enemy(player);
        Integer playerAttackValue;
        Integer enemyAttackValue;
        Integer playerOriginalHp = player.hp;

        Swingy.state.player.attackLog.clear();

        player.attackLog.add("Fight Started!");

        while (true) {
            playerAttackValue = player.attack - RandomInt(enemy.defence / 2, enemy.defence);
            enemyAttackValue = enemy.attack - RandomInt(player.defence - 2, player.defence);

            enemy.hp -= playerAttackValue;
            player.attackLog.add(String.format("You hit enemy for %d", playerAttackValue));

            if (enemy.hp <= 0) {
                player.exp += ((int) (player.level * 1000 + Math.pow(player.level - 1, 2) * 450)) / 3;
                player.hp = 100;
                player.attackLog.add("You win!");
                player.hp = playerOriginalHp;
                return true;
            }

            player.hp -= enemyAttackValue;
            player.attackLog.add(String.format("Enemy hit you for %d", enemyAttackValue));

            if (player.hp <= 0) {
                player.attackLog.add("You lost the fight :(");
                ResetPlayerAfterLoss();
                return false;
            }
        }
    }

    public static void ResetPlayerAfterLoss() {
        String currentPlayerName = Swingy.state.player.name;
        String currentPlayerClass = Swingy.state.player.cls;
        CreateNewHero(currentPlayerClass);
    }

    public static ArrayList<String> ReadAttackLog() {

        ArrayList<String> temp = new ArrayList<String>(Swingy.state.player.attackLog);
        return temp;
    }

    public static void PlaceHeroOnNewMap(Hero player) {
        Map map = new Map(player.level);
        player.x = map.width / 2;
        player.y = map.height / 2;

        Swingy.state.map = map;
    }

    public static Boolean LoadGameFromDatabase(String name) {
        String savedState = Database.LoadGame(name);

        try {
            Swingy.state = GameState.fromString(savedState);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void SaveGameToDatabase() {

        Database.SaveGame(Swingy.state);

    }

    public static void CreateNewHero(String cls) {
        if (cls.equalsIgnoreCase("Warrior")) {
            Swingy.state.player = new Warrior();
        }
        else if (cls.equalsIgnoreCase("Barbarian")) {
            Swingy.state.player = new Barbarian();
        }
        else if (cls.equalsIgnoreCase("Mage")) {
            Swingy.state.player = new Mage();
        }
        PlaceHeroOnNewMap(Swingy.state.player);
    }

    public static void NamePlayer(String name) {
        Swingy.state.player.name = name;
    }

    public static Boolean CheckForEndOfMap() {
        Hero player = Swingy.state.player;
        if (Swingy.state.map.EdgeDectect(Swingy.state.player)) {
            player.exp = ((int) (player.level * 1000 + Math.pow(player.level - 1, 2) * 450)) / 2;
            PlaceHeroOnNewMap(Swingy.state.player);
            return true;
        }
        return false;
    }

    public static Boolean CheckForLevelUp() {
        Hero player = Swingy.state.player;

        Integer level = (int) (player.level * 1000 + Math.pow(player.level - 1, 2) * 450);

        if (player.exp >= level) {
            player.level++;
            player.attack += 2;
            player.defence += 5;
            player.hp += 10;
            return true;
        }
        return false;
    }

    public static Boolean CheckForPowerUp() {
        if (RandomInt(0, 2) == 0) {
            Swingy.state.currentPowerUp = new PowerUp(Swingy.state.player);
            return true;
        }
        return false;
    }

    public static void ApplyPowerUp() {
        Hero player = Swingy.state.player;
        PowerUp powerUp = Swingy.state.currentPowerUp;
        if (powerUp != null) {
            player.attack += powerUp.attack;
            player.defence += powerUp.defence;
            player.hp += powerUp.hp;
        }
    }
}
