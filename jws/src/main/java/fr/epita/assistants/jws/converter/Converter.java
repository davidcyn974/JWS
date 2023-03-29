package fr.epita.assistants.jws.converter;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.presentation.rest.response.CreateGameResponse;

import java.util.ArrayList;
import java.util.List;

public class Converter {


    public static GameEntity gameModel_to_Entity(GameModel gm, PlayerRepository playerRepository)
    {

        List<PlayerModel> nico =  playerRepository.list("game_id",gm.getId());
        List<PlayerEntity> players = new ArrayList<>();

        nico.forEach(lamda -> players.add(playerModel_to_Entity(lamda)));
        GameEntity res = new GameEntity(
                gm.getId(),
                gm.getStarttime(),
                gm.getState(),
                new ArrayList<>(gm.getMap()),
                players

        );
        return  res;
    }

    public static CreateGameResponse gameEntityToCreateResponse(GameEntity gameEntity)
    {
        CreateGameResponse response = new CreateGameResponse(
                gameEntity.getStarttime(),
                gameEntity.getState(),
                convertToInfo(gameEntity.getPlayers()),
                // gameEntity.getPlayers
                gameEntity.getMap(),
                gameEntity.getId()
        );
        return response;
    }
    public static List<PlayerInfo> convertToInfo(List<PlayerEntity> players){
        List<PlayerInfo> res = new ArrayList<>();
        for (PlayerEntity entity : players)
        {
            res.add(new PlayerInfo(entity));
        }
        return res;
    }
    public static PlayerEntity playerModel_to_Entity(PlayerModel pm)
    {
        PlayerEntity res= new PlayerEntity(
                pm.getId(),
                pm.getGame_id(),
                pm.getLastbomb(),
                pm.getLastmovement(),
                pm.getLives(),
                pm.getName(),
                pm.getPosX(),
                pm.getPosY(),
                pm.getPosition());
        return  res;
    }

}