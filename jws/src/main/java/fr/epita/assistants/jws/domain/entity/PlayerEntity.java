package fr.epita.assistants.jws.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlayerEntity {
    public long id;
    public long game_id;

    Timestamp lastbomb;
    Timestamp lastmovement;
    int lives;
    String name;
    int posX;
    int posY;
    int position;
    /*public PlayerEntity(String name , int posx , int posy, int vies, long id)
    {
        this.name = name;
        this.posX = posx;
        this.posY = posy;
        this.lives = vies;
        this.id = id;
    }*/

}