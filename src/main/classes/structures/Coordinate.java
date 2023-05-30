package main.classes.structures;

public class Coordinate {

    int xPos;

    int yPos;

    public Coordinate(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    @Override
    public String toString(){
        char[] horizontalIndices = {'a','b','c','d','e','f','g','h'};
        String verticalIndex = Integer.toString(yPos+1);
        return horizontalIndices[xPos] + verticalIndex;
    }
}
