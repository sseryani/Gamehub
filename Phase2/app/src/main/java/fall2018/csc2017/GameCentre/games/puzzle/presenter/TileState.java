package fall2018.csc2017.GameCentre.games.puzzle.presenter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileState implements Serializable {

    private HashMap<String, String> moveStr;
    private int undos;
    private String dimensions;
    private String highScore;
    private int moves;

    TileState() {}

    TileState(HashMap<String, String> moveStr, int undos, String dimensions, String highScore, int moves) {
        this.moveStr = moveStr;
        this.moves = moves;
        this.undos = undos;
        this.dimensions = dimensions;
        this.highScore = highScore;
    }

    public String getHighScore() { return highScore; }

    public void setHighScore(String highScore) { this.highScore = highScore; }

    public String getLatestMoveStr() {
        for(String s : this.moveStr.keySet()){
            return moveStr.get(s);
        }
        return "9,8,7,6,5,4,3,2,1,";
    }

    public HashMap<String, String> getMoveStr() {
        return moveStr;
    }

    public void setMoveStr(HashMap<String, String> moveStr) {
        this.moveStr = moveStr;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public int getMoves() {
        return moves;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public int getUndos() {
        return undos;
    }

    public void setUndos(int undos) {
        this.undos = undos;
    }
}
