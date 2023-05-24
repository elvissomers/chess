package main.classes.structures;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Player;
import main.classes.pieces.King;
import main.classes.pieces.Pawn;
import main.classes.pieces.Piece;

import java.util.HashSet;
import java.util.Set;

public class PieceSet extends HashSet<Piece> implements IPieceSet{

    private Player player;

    private Set<Square> attackedSquares;

    public PieceSet(Player player) {
        this.player = player;
        this.attackedSquares = new HashSet<>();
        player.setPieceSet(this);
    }

    @Override
    public void setAllMovableSquares(){
        for (Piece piece : this){
            piece.setMovableSquares();
        }

        if (getKing().isInCheck()) {
            // TODO: change movable squares when King is in check, allowing only moves that resolve the check
            Player attackingPlayer = (player.getTeam() == Team.WHITE) ? player.getGame().getBlackPlayer() :
                    player.getGame().getWhitePlayer();
            for (Piece piece : this){
                for (Square square : piece.getMovableSquares()){
                    Board tempBoard = new Board(player.getGame().getBoard());
                    tempBoard.setPiece(square, piece);
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
