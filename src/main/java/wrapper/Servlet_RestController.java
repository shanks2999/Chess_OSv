package wrapper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.art.lach.mateusz.javaopenchess.core.Colors;
import pl.art.lach.mateusz.javaopenchess.core.Game;
import pl.art.lach.mateusz.javaopenchess.core.Square;
import pl.art.lach.mateusz.javaopenchess.core.ai.AI;
import pl.art.lach.mateusz.javaopenchess.core.ai.AIFactory;
import pl.art.lach.mateusz.javaopenchess.core.moves.Move;
import pl.art.lach.mateusz.javaopenchess.core.moves.MovesHistory;
import pl.art.lach.mateusz.javaopenchess.core.pieces.Piece;
import pl.art.lach.mateusz.javaopenchess.core.pieces.implementation.King;
import pl.art.lach.mateusz.javaopenchess.core.players.Player;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerFactory;
import pl.art.lach.mateusz.javaopenchess.core.players.PlayerType;
import pl.art.lach.mateusz.javaopenchess.utils.GameModes;
import pl.art.lach.mateusz.javaopenchess.utils.GameTypes;
import pl.art.lach.mateusz.javaopenchess.utils.Settings;

import java.util.Random;

@RestController
public class Servlet_RestController {
    /**
     * Function defining piece movenment taking player movement and predicting next move
     * **/
    @RequestMapping("/move")
    public GameTracking_BEAN playGame(@RequestParam(value="id") String currentGame, @RequestParam(value="oldx") int old_x, @RequestParam(value="oldy") int old_y,
                                      @RequestParam(value="newx") int new_x,  @RequestParam(value="newy") int new_y) {
        StringBuffer msg= new StringBuffer();
        Game game= StaticHeap.mapMyGames.get(currentGame);
        if(game==null){
            GameTracking_BEAN objGame= new GameTracking_BEAN();
            StaticHeap.LOG4J.info("Game not found");
            objGame.setStatus("Cannot find the game, start a new one.");
            return objGame;
        }

        Square currentPos= game.getChessboard().getSquare(old_x, old_y);
        Square nextPos= game.getChessboard().getSquare(new_x, new_y);
        game.getChessboard().setActiveSquare(currentPos);
        Piece killedPiece;
        GameTracking_BEAN objGame= new GameTracking_BEAN();
        if (isMoveAvailable(nextPos,game))
        {
            game.getChessboard().move(game.getChessboard().getActiveSquare(), nextPos);
            msg.append("Move accepted and "+nextPos.getPiece().getName()+" moved to new position.");
            MovesHistory history=game.getMoves();
            killedPiece=history.getLastMoveFromHistory().getTakenPiece();
            if(killedPiece!=null) {
                objGame.setPieceKilled(killedPiece.getName());
                StaticHeap.LOG4J.info("Piece Killed: "+killedPiece.getName());
            }else{
                objGame.setPieceKilled("N/A");
            }
        }
        else{
            msg.append("Wrong move");
            objGame.setStatus(msg.toString());
            return objGame;
        }
        game.nextMove();

        King king;
        if (game.getSettings().getPlayerWhite().getPlayerType()==PlayerType.COMPUTER)
        {
            king = game.getChessboard().getKingWhite();
        } else
        {
            king = game.getChessboard().getKingBlack();
        }

        if(king.isCheckmatedOrStalemated()!=0) {
            System.out.println("Checkmate or Stalemate!");
            game.setIsEndOfGame(true);
        }
        if (mustAIMove(game) == true)     {
            msg.append("AI made a move");
            Move computer_move= predictNextMove(game);
            PieceTracking_BEAN objPiece= new PieceTracking_BEAN();
            objPiece.setCurrentLocation(computer_move.getFrom().getPozX()+","+computer_move.getFrom().getPozY());
            objPiece.setNextLocation(computer_move.getTo().getPozX()+","+computer_move.getTo().getPozY());
            objGame.setObjPieceTracking(objPiece);
            objPiece.setPiece(computer_move.getMovedPiece().getName());
            Piece p=computer_move.getTakenPiece();
            if(p!=null){
                objPiece.setPieceKilled(p.getName());}
            else{
                objPiece.setPieceKilled("N/A");
            }
        }
        objGame.setStatus(msg.toString());
    return objGame;
    }


    /**
     * Function for creating new game. Assigns White/Black setup to players according to params passed.
     * Makes first move if its white
     * **/
    @RequestMapping("/newgame")
    public InitializeGame_BEAN startGame(@RequestParam(value="iswhite",defaultValue="false") Boolean isWhite,
                                         @RequestParam(value="username",defaultValue="shanks") String userName,
                                         @RequestParam(value="level",defaultValue="2") int level) {
        StaticHeap.LOG4J.info("Setting up a new Game...");
        InitializeGame_BEAN beanInitialize= new InitializeGame_BEAN();
        String id = String.valueOf(generateRandomID());
        beanInitialize.setId(id);
        String userNameWhite;
        String userNameBlack;
        PlayerType userTypeWhite;
        PlayerType userTypeBlack;
        if(isWhite){
            userNameBlack = userName;
            userNameWhite = "AI";
            userTypeBlack = PlayerType.LOCAL_USER;
            userTypeWhite = PlayerType.COMPUTER;
            StaticHeap.LOG4J.info("You are BLACK.");
        }
        else{
            userNameBlack = "AI";
            userNameWhite = userName;
            userTypeBlack = PlayerType.COMPUTER;
            userTypeWhite = PlayerType.LOCAL_USER;
            StaticHeap.LOG4J.info("You are WHITE.");
        }
        Player userWhite = PlayerFactory.getInstance(userNameWhite, Colors.WHITE, userTypeWhite);
        Player userBlack = PlayerFactory.getInstance(userNameBlack, Colors.BLACK, userTypeBlack);
        Game game = new Game();
        StaticHeap.mapMyGames.put(id,game);
        Settings gameSettings = game.getSettings();
        gameSettings.setPlayerWhite(userWhite);
        gameSettings.setPlayerBlack(userBlack);
        gameSettings.setGameMode(GameModes.NEW_GAME);
        gameSettings.setGameType(GameTypes.LOCAL);
        gameSettings.setUpsideDown(false);
        game.setActivePlayer(userWhite);
        System.out.println("New game Created: "+ userWhite.getName() + " vs. " + userBlack.getName()+ "\ntimer: " + gameSettings.getTimeForGame()
                + "\ntime limit set: " + gameSettings.isTimeLimitSet() + "\nwhite on top?: " + gameSettings.isUpsideDown());
        game.getChessboard().setPieces4NewGame(game.getSettings().getPlayerWhite(), game.getSettings().getPlayerBlack());
        StringBuffer msg = new StringBuffer();
        msg.append("New Game is Created");
        if (isWhite)
        {
            AI ai = AIFactory.getAI(level);
            game.setAi(ai);
            if (willAIRespond(game) == true)       {
                Move first_move= predictNextMove(game);
                PieceTracking_BEAN mi= new PieceTracking_BEAN();
                mi.setCurrentLocation(first_move.getFrom().getPozX()+","+first_move.getFrom().getPozY());
                mi.setNextLocation(first_move.getTo().getPozX()+","+first_move.getTo().getPozY());
                beanInitialize.setObjPieceTracking(mi);
                mi.setPiece(first_move.getMovedPiece().getName());
                Piece p=first_move.getTakenPiece();
                if(p!=null){
                    mi.setPieceKilled(p.getName());}
                else{
                    mi.setPieceKilled("N/A");
                }
                msg.append(", AI moves first, your turn");
                StaticHeap.LOG4J.info("First move is AI.");
            }
            else{
                msg.append(",your turn");
            }
        }
        beanInitialize.setStatus(msg.toString());
        return beanInitialize;
    }


    /**
     * Function to terminate the chess instance.
     * Checks the ID in the mak and removes it
     * **/
    @RequestMapping("/quitgame")
    public WrapUp_BEAN quit(@RequestParam(value="id") String id) {
        StaticHeap.mapMyGames.remove(id);
        WrapUp_BEAN er= new WrapUp_BEAN();
        er.setResponse("Game is terminated.");
        StaticHeap.LOG4J.info("Session is finished and game is terminated");
        return er;
    }


    private static boolean willAIRespond(Game activeGame)    {
        return activeGame.getSettings().isGameAgainstComputer()
                && activeGame.getSettings().getPlayerWhite().getPlayerType() == PlayerType.COMPUTER;
    }

    private static boolean mustAIMove(Game game)
    {
        return !game.isIsEndOfGame() && game.getSettings().isGameAgainstComputer()
                && game.getActivePlayer().getPlayerType() == PlayerType.COMPUTER && null != game.getAi();
    }

    private static boolean isMoveAvailable(Square sq, Game game)    {
        Square activeSq = game.getChessboard().getActiveSquare();
        return activeSq != null && activeSq.piece != null && activeSq.getPiece().getAllMoves().contains(sq);
    }

    public static Move predictNextMove(Game game)    {
        Move lastMove = game.getMoves().getLastMoveFromHistory();
        Move move = game.getAi().getMove(game, lastMove);
        game.getChessboard().move(move.getFrom(), move.getTo());
        if (null != move.getPromotedPiece())
        {
            move.getTo().setPiece(move.getPromotedPiece());
        }
        game.nextMove();
        return move;
    }

    public static int generateRandomID() {
        Random rand = new Random();
        int num = rand.nextInt(9000000) + 1000000;
        return num;
    }
}
