package models;

import helpers.RandomIndexOfStringArrayListPicker;

import java.io.Serializable;

public class PowerUp implements Serializable {

    public Integer attack = 0;
    public Integer defence = 0;
    public String name = null;
    public Integer hp = 0;
    public String typeOfPowerUp;

    public PowerUp(Hero player) {
        String powerUpSelectedRandomlyFromArrayOfStrings;
        RandomIndexOfStringArrayListPicker<String> powerUpType = new RandomIndexOfStringArrayListPicker<String>();
        powerUpType.add("weapon");
        powerUpType.add("armor");
        powerUpType.add("helm");

        powerUpSelectedRandomlyFromArrayOfStrings = powerUpType.GetRandomItemFromArrayListUsingArraySize();

        if (powerUpSelectedRandomlyFromArrayOfStrings.equalsIgnoreCase("weapon")) {
            this.attack = player.level + 1;
            this.name = EntityNames.selectRandomPowerUpName("weapon");
        }
        else if (powerUpSelectedRandomlyFromArrayOfStrings.equalsIgnoreCase("armor")) {
            this.defence = player.level + 1;
            this.name = EntityNames.selectRandomPowerUpName("armor");
        }
        else if (powerUpSelectedRandomlyFromArrayOfStrings.equalsIgnoreCase("helm")) {
            this.hp = player.level + 10;
            this.name = EntityNames.selectRandomPowerUpName("helm");
        }

        this.typeOfPowerUp = powerUpSelectedRandomlyFromArrayOfStrings;
    }
}
