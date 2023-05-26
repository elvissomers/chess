package main.classes.structures;

import main.classes.board.Board;
import main.classes.board.Square;
import main.classes.controllers.Game;
import main.classes.controllers.Player;
import main.classes.pieces.*;

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
                    // TODO: Piece tempPiece = new Piece(piece);
                    Piece tempPiece;
                    if (piece instanceof Knight)
                        tempPiece = new Knight(piece);
                    else if (piece instanceof Bishop)
                        tempPiece = new Bishop(piece);
                    else if (piece instanceof Rook)
                        tempPiece = new Rook(piece);
                    else if (piece instanceof Queen)
                        tempPiece = new Queen(piece);
                    else if (piece instanceof King)
                        tempPiece = new King(piece);
                    else if (piece instanceof Pawn)
                        tempPiece = new Pawn(piece);
                    else { // Not supposed to trigger, but used to handle exceptions
                        System.out.println("Warning: not a valid Piece Type!");
                        tempPiece = new Pawn(player.getGame(), Team.WHITE);
                    }

                    tempPiece.setSquare(tempBoard.getSquareByPos(square.getHorizontalPosition(),
                            square.getVerticalPosition()));
//                    tempBoard.setPiece(tempBoard.getSquareByPos(square.getHorizontalPosition(),
//                            square.getVerticalPosition()), piece);

                    Player tempEnemyPlayer = new Player(enemyPlayer);

                    PieceSet tempEnemyPieces = new PieceSet(tempEnemyPlayer, enemyPlayer.getPieceSet());
                    tempEnemyPieces.setAllAttackedSquares();
                    tempEnemyPlayer.setPieceSet(tempEnemyPieces);

                    Game tempGame = new Game(player.getGame());
                    tempGame.setBoard(tempBoard);
                    tempPiece.setGame(tempGame);


                    Player tempPlayer = new Player(player);
                    tempPlayer.setGame(tempGame);
                    tempEnemyPlayer.setGame(tempGame);
                    PieceSet tempPieces = new PieceSet(tempPlayer, this);
//                    tempPieces.get(piece).setSquare();
                    // TODO: bug: the moved piece is not moved inside tempPieces
                    tempPieces.remove(piece);
                    tempPieces.add(tempPiece);
                    for (Piece anyTempPiece : tempPieces){
                        anyTempPiece.setBoard(tempBoard);
                        anyTempPiece.setGame(tempGame);
                    }
                    for (Piece anyEnemyTempPiece : tempEnemyPieces){
                        anyEnemyTempPiece.setBoard(tempBoard);
                        anyEnemyTempPiece.setGame(tempGame);
                    }

                    tempPlayer.setPieceSet(tempPieces);
                    if (player.getTeam() == Team.WHITE) {
                        tempGame.setWhitePlayer(tempPlayer);
                        tempGame.setBlackPlayer(tempEnemyPlayer);
                    }
                    else {
                        tempGame.setWhitePlayer(tempEnemyPlayer);
                        tempGame.setBlackPlayer(tempPlayer);
                    }

                    King tempKing = tempPieces.getKing();

                    // TODO: what if king was just moved?
                    if (tempKing.checkCheck(tempBoard.getSquareByPos(getKing().getSquare().getHorizontalPosition(),
                            getKing().getSquare().getVerticalPosition()))) {
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
