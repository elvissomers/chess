package main.classes.structures;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.pieces.King;
import main.classes.pieces.Pawn;
import main.classes.pieces.Piece;

import java.util.*;

public class PieceSet extends HashSet<Piece> implements IPieceSet{

    //TODO: reimplement pieceSet to pieceMap
    private Player player;

    private Set<Square> attackedSquares;

    public PieceSet(Player player) {
        this.player = player;
        this.attackedSquares = new HashSet<>();
        player.setPieceSet(this);
    }

    public PieceSet(Player player, PieceSet other){
        this.attackedSquares = new HashSet<>();
        for (Piece piece : other){
            this.add(piece);
        }
        player.setPieceSet(this);
    }

    @Override
    public void setAllMovableSquares(){
        for (Piece piece : this){
            piece.setMovableSquares();
        }

        if (getKing().checkCheck(getKing().getSquare()))
            getKing().setInCheck(true);

        if (getKing().isInCheck()) {
            for (Piece piece : this){
                List<Square> squaresToRemove = new ArrayList<>();
                Player enemyPlayer = (player.getTeam() == Team.WHITE) ? player.getGame().getBlackPlayer() :
                        player.getGame().getWhitePlayer();
                for (Square square : piece.getMovableSquares()){
                    Board tempBoard = new Board(player.getGame().getBoard());
                    tempBoard.setPiece(tempBoard.getSquareByPos(square.getHorizontalPosition(),
                            square.getVerticalPosition()), piece);

                    Player tempEnemyPlayer = new Player(enemyPlayer);
                    PieceSet tempEnemyPieces = new PieceSet(tempEnemyPlayer, enemyPlayer.getPieceSet());
                    tempEnemyPieces.setAllAttackedSquares();
                    tempEnemyPlayer.setPieceSet(tempEnemyPieces);

                    Game tempGame = new Game(player.getGame());
                    tempGame.setBoard(tempBoard);

                    Player tempPlayer = new Player(player);
                    PieceSet tempPieces = new PieceSet(tempPlayer, this);
                    tempPieces.get(piece).setSquare()
                    tempPlayer.setPieceSet(tempPieces);
                    if (player.getTeam() == Team.WHITE) {
                        tempGame.setWhitePlayer(tempPlayer);
                        tempGame.setBlackPlayer(tempEnemyPlayer);
                    }
                    else {
                        tempGame.setWhitePlayer(tempEnemyPlayer);
                        tempGame.setBlackPlayer(tempPlayer);
                    }

                    King tempKing = new King(tempGame, player.getTeam());
                    // TODO: what if king was just moved?
                    if (tempKing.checkCheck(getKing().getSquare())) {
                        squaresToRemove.add(square);
                    }

                }
                for (Square square : squaresToRemove){
                    piece.getMovableSquares().remove(square);
                }
            }
        }
    }

    @Override
    public void setAllAttackedSquares() {
        for (Piece piece : this){
            if (piece instanceof Pawn pawn) {
                pawn.setAttackedSquares();
                attackedSquares.addAll(pawn.getAttackedSquares());
            } else {
                piece.setMovableSquares();
                attackedSquares.addAll(piece.getMovableSquares());
            }
        }
    }

    @Override
    public Set<Square> getAllAttackedSquares() {
        return attackedSquares;
    }

    @Override
    public King getKing() {
        for (Piece piece : this) {
            if (piece instanceof King) {
                return (King) piece;
            }
        }
        return null;
    }
}
