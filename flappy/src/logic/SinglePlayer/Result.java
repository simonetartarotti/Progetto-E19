package logic.SinglePlayer;

import logic.player.Player;

public class Result implements Comparable{

    private String playerName;
    private int score;

    public Result() {
    }

    public Result(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public Result(Player player) {
        playerName = player.getPlayerInfo().getName();
        score = player.getScore();
    }

    public void setRecord(Player player){
        this.score = player.getScore();
    }

    public void setName(String name){
        this.playerName = name;
    }

    public String getName(){
        return playerName;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

    @Override
    public int compareTo(Object o) {
        return ((Result)o).getScore()-getScore();
    }
}
