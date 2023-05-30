package main.classes.structures;

import main.classes.controllers.Player;
import main.classes.pieces.King;
import main.classes.pieces.Piece;

import java.util.HashSet;

public class NewPieceSet extends HashSet<Piece> {

    private Player player;

    private King king;

    public NewPieceSet(Player player){
        this.player = player;
    }

    public void setKing() {
        for (Piece piece : this){
            if (piece instanceof King){
                king = (King) piece;
            }
        }
    }

    public King getKing(){
        return king;
    }
}
