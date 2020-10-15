package views.console;

import controllers.Database;
import controllers.Game;
import controllers.ModelValidation;
import helpers.Character;
import swingy.Swingy;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleView {
    private final Scanner input = new Scanner(System.in);
    private final static ArrayList<String> errors = new ArrayList<>();
    private final static ArrayList<String> info = new ArrayList<>();

    public ConsoleView() {
        MainMenu();
    }

    private static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (String error : errors) {
            System.out.println(error);
        }
        errors.clear();
        for (String info : info) {
            System.out.println(info);
        }
        info.clear();
    }

    public String GetUserInput(String prompt) {
        System.out.print("\n" + prompt);

        String selection = input.nextLine();

        if (selection.equalsIgnoreCase("q") || selection.equalsIgnoreCase("quit")) {
            System.out.println("Thanks for playing! Goodbye.");
            System.exit(0);
        }
        return selection;
    }

    public void MainMenu() {
        ClearScreen();

        Swingy.state.currentScreen = "main_menu";
        System.out.println("Welcome to Swingy");
        System.out.println("Tye \"q\" or \"quit\" at anytime to exit");
        System.out.println(" ");
        System.out.println("1) Create a new hero");
        System.out.println("2) Select existing hero");

        String selection = GetUserInput("Selection : ");

        if (selection.equalsIgnoreCase("1")) {
            CreateNewHero();
        } else if (selection.equalsIgnoreCase("2")) {
            SelectExistingHero();
        } else if (selection.equalsIgnoreCase("q")) {
            System.exit(0);
        } else {
            errors.add("Invalid selection, please choose a valid option.");
            MainMenu();
        }
    }

    public void SelectExistingHero() {
        ClearScreen();

        Swingy.state.currentScreen = "select_existing_hero";
        ArrayList<Character> characters = Database.LoadSavedCharacters();

        if (characters.size() == 0) {
            errors.add("There are no saved games currently");
            MainMenu();
        }

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            System.out.printf("%d) %20s %-10s lvl %d%n", i + 1, c.name, c.cls, c.level);
        }

        String selString = GetUserInput("Selection : ");

        Integer selection = -1;

        try {
            selection = Integer.parseInt(selString);
        } catch (NumberFormatException e) {
            errors.add("Please select a number");
            SelectExistingHero();
        }

        if (selection > characters.size()) {
            errors.add("Please select a number in the correct range");
            SelectExistingHero();
        }

        if (!Game.LoadGameFromDatabase((characters.get(selection - 1).name))) {
            errors.add("Could not load save game :(");
            MainMenu();
        }
        GameLoop();
    }

    public void CreateNewHero() {
        ClearScreen();
        Swingy.state.currentScreen = "new_hero";
        System.out.println("New hero selection");
        System.out.println("");
        System.out.println("Select a class for your new hero");
        System.out.println("1) Barbarian");
        System.out.println("2) Warrior");
        System.out.println("3) Mage");

        String selection = GetUserInput("Selection : ");

        if (selection.equalsIgnoreCase("1")) {
            Game.CreateNewHero("Barbarian");
        } else if (selection.equalsIgnoreCase("2")) {
            Game.CreateNewHero("Warrior");
        } else if (selection.equalsIgnoreCase("3")) {
            Game.CreateNewHero("Mage");
        } else {
            CreateNewHero();
        }
        NameHero();
    }

    public void NameHero() {
        ClearScreen();
        Swingy.state.currentScreen = "name_hero";
        System.out.println("Choose a name for your hero");
        System.out.println("");
        System.out.println("Name must be between 3 and 20 characters");

        String name = GetUserInput("Name : ");

        Game.NamePlayer(name);

        if (ModelValidation.validatePlayer(Swingy.state.player)) {
            Game.PlaceHeroOnNewMap(Swingy.state.player);
            GameLoop();
        } else {
            errors.add(String.format("'%s' is not a valid name. Please choose another one", name));
            Game.NamePlayer("");
            NameHero();
        }
    }

    public void EndOfMap() {
        System.out.println("You have reached the end of this map");
        GetUserInput("Press enter to continue");
        GameLoop();
    }

    public void GameLoop() {
        Boolean encounter = true;
        Swingy.state.currentScreen = "game_loop";
        ClearScreen();

        Game.SaveGameToDatabase();

        if (Game.CheckForEndOfMap()) EndOfMap();
        if (Game.CheckForLevelUp()) LevelUp();

        System.out.print(String.format("You are at position [%d,%d]", Swingy.state.player.x, Swingy.state.player.y));
        System.out.println(String.format(" on a (%d x %d) map", Swingy.state.map.width, Swingy.state.map.height));
        System.out.println("Which direction would you like to move?");
        System.out.println("");
        System.out.println("1) North");
        System.out.println("2) East");
        System.out.println("3) South");
        System.out.println("4) West");
        System.out.println("5) Show player stats");

        String selection = GetUserInput("Selection : ");

        if (selection.equalsIgnoreCase("1")) {
            encounter = Game.MovePlayerUpAndCheckForEncounter();
        } else if (selection.equalsIgnoreCase("2")) {
            encounter = Game.MovePlayerRightAndCheckForEncounter();
        } else if (selection.equalsIgnoreCase("3")) {
            encounter = Game.MovePlayerDownAndCheckForEncounter();
        } else if (selection.equalsIgnoreCase("4")) {
            encounter = Game.MovePlayerLeftAndCheckForEncounter();
        } else if (selection.equalsIgnoreCase("5")) {
            ClearScreen();
            PrintStats();
            GetUserInput("Press enter to continue");
            GameLoop();
        } else {
            errors.add(String.format("Incorrect selection"));
            GameLoop();
        }

        if (encounter) {
            Encounter();
        } else {
            info.add("No enemies encountered");
            GameLoop();
        }
    }

    public void PrintStats() {
        System.out.println("Player stats");
        System.out.println("");
        System.out.println("Name       : " + Swingy.state.player.name);
        System.out.println("Class      : " + Swingy.state.player.cls);
        System.out.println("Level      : " + Swingy.state.player.level);
        System.out.println("Attack     : " + Swingy.state.player.attack);
        System.out.println("Defence    : " + Swingy.state.player.defence);
        System.out.println("Experience : " + Swingy.state.player.exp);
    }

    public void LevelUp() {
        Swingy.state.currentScreen = "level_up";

        System.out.println("You have leveled up! Well Done :)");
        PrintStats();

        GetUserInput("Press enter to continue");
    }

    public void Encounter() {
        ClearScreen();
        Swingy.state.currentScreen = "encounter";
        System.out.println("You have encountered an enemy!");
        System.out.println("You can try to escape but it may not work, or fight this enemy");
        System.out.println("");
        System.out.println("1) Fight");
        System.out.println("2) Escape");
        System.out.println("");

        String selection = GetUserInput("Selection : ");

        if (selection.equalsIgnoreCase("1")) {
            Battle();
        } else if (selection.equalsIgnoreCase("2")) {
            if (Game.TryEscape()) {
                info.add("Successfully escaped encounter");
                Swingy.state.currentScreen = "escaped";
                GameLoop();
            } else {
                errors.add("Could not escape, must fight!");
                Battle();
            }
        } else {
            errors.add("Incorrect selection");
            Encounter();
        }
    }

    public void Battle() {

        ClearScreen();
        Swingy.state.currentScreen = "battle";
        Boolean fightWon = Game.Fight();

        (Game.ReadAttackLog()).forEach(entry -> System.out.println(entry));

        if (fightWon) {
            if (Game.CheckForPowerUp()) {
                PowerUpObtained();
            }
        } else {
            FightHasBeenLost();
        }

        GetUserInput("Press enter to continue");
        GameLoop();
    }

    public void PowerUpObtained() {
        ClearScreen();
        Swingy.state.currentScreen = "powerup";
        System.out.println("The enemy dropped an artifact");
        System.out.println(String.format("Name      : %s",  Swingy.state.currentPowerUp.name));
        System.out.println(String.format("Type      : %s",  Swingy.state.currentPowerUp.typeOfPowerUp));
        System.out.println(String.format("Attack    : +%d", Swingy.state.currentPowerUp.attack));
        System.out.println(String.format("Defence   : +%d", Swingy.state.currentPowerUp.defence));
        System.out.println(String.format("HP        : +%d", Swingy.state.currentPowerUp.hp));
        System.out.println("");
        System.out.println("1) Keep it");
        System.out.println("2) Move on");

        String selection = GetUserInput("Selection : ");

        if (selection.equalsIgnoreCase("1")) {
            Game.ApplyPowerUp();
            GameLoop();
        } else if (selection.equalsIgnoreCase("2")) {
            GameLoop();
        } else {
            errors.add("Incorrect selection");
            PowerUpObtained();
        }
    }

    public void FightHasBeenLost() {
        System.out.println("GAME OVER!");
        System.out.println("Your character will be reset to level 0");

        String selection = GetUserInput("Press enter to go back to the main menu");

        MainMenu();
    }
}
