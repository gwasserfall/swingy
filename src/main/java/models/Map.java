package models;

import java.io.Serializable;


public class Map implements Serializable {

    public Integer width;
    public Integer height;

    public Map(int level) {
        Integer mapSize = (level - 1) * 5 + 10 - (level % 2);

        this.width = mapSize;
        this.height = mapSize;
    }

    public Boolean EdgeDectect(Hero player) {
        if (player.x == 0 || player.y == 0) {
            return true;
        }
        if (player.x >= this.width || player.y >= height) {
            return true;
        }
        return false;
    }
}
