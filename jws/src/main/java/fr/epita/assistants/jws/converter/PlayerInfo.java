package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import lombok.Data;

@Data
public class PlayerInfo {

    long id;
    String name;
    int lives;
    int posX;
    int posY;


    public PlayerInfo(PlayerEntity playerEntity) {
        this.id = playerEntity.getId();
        this.name = playerEntity.getName();
        this.lives = playerEntity.getLives();
        this.posX = playerEntity.getPosX();
        this.posY = playerEntity.getPosY();

    }
}
