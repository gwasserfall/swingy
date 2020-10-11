package swingy;

import controllers.Database;
import models.GameState;
import views.console.ConsoleView;

import javax.validation.Validation;
import javax.validation.Validator;
import java.sql.SQLException;
import java.util.logging.Level;

public class Swingy {

    public static GameState state = new GameState();;
    public static Validator validator;

    public static void main(String[] args) throws Exception {

        String display = "none";
        if (args.length != 1) {
            System.out.println("Please specify view type 'gui' or 'console'");
            System.exit(1);
        }

        try {
            InitializeGame();
        } catch (Exception e) {
            System.out.println("Could not initialize game: " + e.getMessage());
            System.exit(1);
        }

        if (args[0].equalsIgnoreCase("gui")) {
            display = "gui";
        } else if (args[0].equalsIgnoreCase("console")) {
            display = "console";
        } else {
            System.out.println("Argument must be 'gui' or 'console'");
            System.exit(1);
        }

        while (true) {
            if (display == "console") {
                new ConsoleView();
            }
        }

    }

    public static void InitializeGame() throws SQLException {
        // Set hibernate validation logging to severe only.
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        // Ensure database is correctly configured
        Database.CheckDatabaseSchema();

        // Create validator
        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}

