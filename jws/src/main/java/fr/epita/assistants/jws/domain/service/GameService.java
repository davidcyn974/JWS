package fr.epita.assistants.jws.domain.service;

import fr.epita.assistants.jws.data.model.GameModel;
import fr.epita.assistants.jws.data.model.PlayerModel;
import fr.epita.assistants.jws.data.repository.GameRepository;
import fr.epita.assistants.jws.data.repository.PlayerRepository;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import org.apache.commons.lang3.NotImplementedException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static fr.epita.assistants.jws.converter.Converter.gameModel_to_Entity;
import static fr.epita.assistants.jws.converter.Converter.playerModel_to_Entity;
import static java.lang.Math.abs;

@ApplicationScoped
@Transactional
public class GameService {

    // static GameRepository gameRepository = new GameRepository();
    //static PlayerRepository playerRepository = new PlayerRepository();
    @Inject
    GameRepository gameRepository;
    @Inject
    PlayerRepository playerRepository;

    public List<GameEntity> getAllGames() {
        List<GameEntity> res = new ArrayList<>();
        for (GameModel gm : gameRepository.listAll()) {
            res.add(gameModel_to_Entity(gm, playerRepository));
        }
        return res;
    }

    public GameEntity create(String name) {
        System.out.println("ON EST DANS CREATE");
        GameModel game = new GameModel();
        String mapPath = System.getenv("JWS_MAP_PATH");
        try {
            List<String> map = Files.readAllLines(Paths.get(mapPath));
            game.setMap(new ArrayList<>(map));
            System.out.println(map);
        } catch (IOException e) {
            System.out.println("Can not open file");
            e.printStackTrace();
        }
        game.setState("STARTING");
        game.setStarttime(Timestamp.valueOf(LocalDateTime.now()));
        PlayerModel player1 = new PlayerModel();
        gameRepository.persist(game);
        player1.setGame_id(game.getId());
        player1.setLives(3);
        player1.setPosX(1);
        player1.setPosY(1);
        player1.setName(name);
        playerRepository.persist(player1);
        // convert
        return gameModel_to_Entity(game, playerRepository);
    }

    public GameEntity getGameById(long gameId) {
        if (gameRepository.findById(gameId) == null)
            return null;
        return gameModel_to_Entity(gameRepository.findById(gameId), playerRepository);
    }


    // ////////////////// new
    public GameEntity join(Long gameId, String request) {

        // request = player name
        // gameId = game id get by find by ID
        GameModel model = gameRepository.findById(gameId);
        PlayerModel newPlayer = new PlayerModel();
        newPlayer.setGame_id(gameId); // assign player to game
        newPlayer.setName(request);
        newPlayer.setLives(3);

        GameEntity entity = gameModel_to_Entity(model, playerRepository);
        int taille = entity.getPlayers().size(); // nombre total de joueurs dans la game
        if (taille == 1) // un seul joueur dans la game
        {
            newPlayer.setPosX(15);
            newPlayer.setPosY(1);
        } else if (taille == 2) // 2 joueurs dans la game
        {
            newPlayer.setPosX(15);
            newPlayer.setPosY(13);
        } else if (taille == 3) // 3 joueurs dans la game
        {
            newPlayer.setPosX(1);
            newPlayer.setPosY(13);
        } else // deja 4 players
        {
            return null;
        }
        playerRepository.persist(newPlayer);
        return gameModel_to_Entity(model, playerRepository);
    }


    public GameEntity start(long gameId) {
        GameModel model = gameRepository.findById(gameId);
        String etat = model.getState();
        if (etat.equals("STARTING"))
            model.setState("RUNNING");

        // check if only 1 player then update to FINISH
        List<PlayerModel> list_players = playerRepository.list("game_id", model.getId());
        if (list_players.size() == 1)
            model.setState("FINISHED");
        //gameRepository.update(model.getState());
        return gameModel_to_Entity(model, playerRepository);
    }

    public GameEntity movePlayer(long gameId,
                                 long playerId,
                                 int posX,
                                 int posY) {
        GameModel gameModel = gameRepository.findById(gameId);
        PlayerModel playerModel = playerRepository.findById(playerId);
        // diagonale :
        if((playerModel.getPosX() == posX && playerModel.getPosY() != posY))
        {
            if(playerModel.getPosY() - posY == 1 || playerModel.getPosY() - posY == -1)
            {
                playerModel.setPosY(posY);
            }
            else
            {
                return null;
            }
        }
        else if((playerModel.getPosY() == posY && playerModel.getPosX() != posX))
        {
            if(playerModel.getPosX() - posX == 1 || playerModel.getPosX() - posX == -1)
            {
                playerModel.setPosX(posX);
            }
            else
            {
                return null;
            }
        }
        else {
            return null;
        }
        return gameModel_to_Entity(gameModel,playerRepository);
    }


    public GameEntity getplayerByid(long playerId) {
         if (playerRepository.findById(playerId) == null)
            return null;
        return gameModel_to_Entity(gameRepository.findById(playerId), playerRepository);

    }

    public boolean checkIDS(long gameId, long playerId) {
        return (gameRepository.findById(gameId) == null  ||  playerRepository.findById(playerId) == null);
    }

}