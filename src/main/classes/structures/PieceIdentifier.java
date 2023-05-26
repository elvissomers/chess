package main.classes.structures;

public class PieceIdentifier {
    /**
     * Aggregate of PieceType enum and int to identify each piece
     */
    PieceType pieceType;

    int pieceNumber;

    public PieceIdentifier(PieceType pieceType, int pieceNumber) {
        this.pieceType = pieceType;
        this.pieceNumber = pieceNumber;
    }

    // Should team be in here or not?
}
