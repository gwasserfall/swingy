package models;

import java.io.*;
import java.util.Base64;

public class GameState implements Serializable {

    public String currentScreen;
    public Hero player;
    public Boolean gameStarted = false;
    public Map map;
    public Boolean fightWon = false;
    public PowerUp currentPowerUp = null;
    public Enemy currentEnemy = null;

    public GameState() { }

    public String stringify( ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    public static GameState fromString( String s ) throws IOException, ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return (GameState) o;
    }

}
