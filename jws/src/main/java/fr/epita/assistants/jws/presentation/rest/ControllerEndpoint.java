package fr.epita.assistants.jws.presentation.rest;

import fr.epita.assistants.jws.converter.PlayerInfo;
import fr.epita.assistants.jws.domain.entity.GameEntity;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import fr.epita.assistants.jws.domain.service.GameService;
import fr.epita.assistants.jws.presentation.rest.request.MovePlayerRequest;
import fr.epita.assistants.jws.presentation.rest.request.Request;
import fr.epita.assistants.jws.presentation.rest.response.CreateGameResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameDetailResponse;
import fr.epita.assistants.jws.presentation.rest.response.GameListResponse;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static fr.epita.assistants.jws.converter.Converter.gameEntityToCreateResponse;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/games")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class ControllerEndpoint {
    /* my functions : */

    // GET /games : Get All Games
    @Inject
    GameService gameService;

    @GET
   // @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGames() {
        System.out.println("GetAllGames Begin");
        List<GameEntity> games = gameService.getAllGames();
        System.out.println("get all games function done");
        List<GameListResponse> res = new ArrayList<>();
        System.out.println("GetAllGames End");
        for (GameEntity game : games) {
            res.add(new GameListResponse(game.getId(), game.getPlayers().size(), game.getState()));
        }
        return Response.status(200).entity(res).build();
    }

    // POST /games : Create a new game

    @POST
    @Transactional
   // @Consumes(MediaType.APPLICATION_JSON)
  //  @Produces(MediaType.APPLICATION_JSON)
    public Response createGame(Request request) {
        System.out.println("Create Game Begin");
        if (request.getName() == null ){
            System.out.println("Create Game Exceptions cases");
            return Response.status(400).build();
        }
        System.out.println("Create Game no Exception Good");
        //String playerName = request.getPlayerName();
        GameEntity game = gameService.create(request.getName());
        System.out.println("Return part");
        CreateGameResponse response = gameEntityToCreateResponse(game);
        return Response.status(200).entity(response).build();
    }


    // GET /games/{gameId} : Get Games info
    @GET
   // @Produces(APPLICATION_JSON)
    @Transactional
    @Path("/{gameId}")
    public Response get_game_info(@PathParam("gameId") long gameId) {
        // TODO
         System.out.println("Begin GET PARTICULAR GAME INFO");
         GameEntity game = gameService.getGameById(gameId);
         if(game == null)
             return Response.status(404).build();
         List<PlayerInfo> playerInfosList = new ArrayList<>();
        for (PlayerEntity player: game.getPlayers()) {
            playerInfosList.add(new PlayerInfo(player));
                    //player.getId(), player.getLives(),player.getPosx(),player.getPosy(),player.getName()));
        }
         GameDetailResponse gameDetailResponse = new GameDetailResponse(
                 game.getStarttime(),
                ///game.getPlayers(),
                 playerInfosList,
                game.getId(),
                game.getState(),
                game.getMap()
         );
        return Response.status(200).entity(gameDetailResponse).build();

    }

    // POST /games/{gameId} : Join a game
    @POST
   // @Consumes(APPLICATION_JSON)
    //@Produces(APPLICATION_JSON)
    @Path("/{gameId}")
    public Response join_game(@PathParam("gameId") Long gameId , Request request) {

       /* ERR : Si la requete est null ou body null */
        if (request == null ||request.getName() == null )
             return  Response.status(400).build();
        // find the game
        GameEntity game = gameService.getGameById(gameId);
        // ERR : cas ou pas reussi a find the game
        if(game == null)
            return  Response.status(404).build();
        game  = gameService.join(gameId , request.name);
        if(game == null) // cas ou add player > 4
            return Response.status(400).build();
        if(game.getState().equals("RUNNING") || game.getState().equals("FINISHED"))
             return Response.status(400).build();
        CreateGameResponse res = gameEntityToCreateResponse(game);
        return Response.status(200).entity(res).build();

    }


    // PATCH /games{gameId}/start
    @PATCH
    @Path("{gameId}/start")
   // @Produces(APPLICATION_JSON)
    public Response startGame(@PathParam("gameId") long gameId)
    {
        GameEntity game = gameService.getGameById(gameId);
        if(game == null)
            return Response.status(404).build();
        // is game null
        if(game.getState().equals("RUNNING") || game.getState().equals("FINISHED"))
             return Response.status(404).build();
        game  = gameService.start(gameId);
        CreateGameResponse res = gameEntityToCreateResponse(game);
        return Response.status(200).entity(res).build();
    }

   // @Consumes(APPLICATION_JSON)
   // @Produces(APPLICATION_JSON)
    @POST
    @Path("/{gameId}/players/{playerId}/move")
    public Response movePlayer(
                            @PathParam("gameId") long gameId,
                            @PathParam("playerId") long playerId,
                            MovePlayerRequest request)
    {
        if(request.getPosX() == null || request.getPosY() == null)
        {
            return Response.status(400).build();
        }
        GameEntity game = gameService.getGameById(gameId);
        if(game == null)
            return Response.status(404).build();// game does not exist

        GameEntity player = gameService.getplayerByid(playerId);
        if(player == null)
        {
            return Response.status(404).build(); // player does not exist
        }
        if(!(game.getState().equals("RUNNING")))
        {
            return Response.status(400).build(); // game not running
        }
        game =  gameService.movePlayer(gameId, playerId, request.getPosX(), request.getPosY());
        if(game == null) //
        {
            return Response.status(400).build();
        }
        CreateGameResponse res = gameEntityToCreateResponse(game);
        return Response.status(200).entity(res).build();
    }

    @POST
    @Path("/{gameId}/players/{playerId}/bomb")
    public Response BOMBA(@PathParam("gameId") long gameId,
                            @PathParam("playerId") long playerId,
                            MovePlayerRequest request)
    {
        if(gameService.checkIDS(gameId, playerId))
        {
            return Response.status(404).build(); // player does not exist
        }
        //CreateGameResponse res = gameEntityToCreateResponse(game);
        //return Response.status(200).entity(res).build();
        return Response.status(400).build();
    }

}