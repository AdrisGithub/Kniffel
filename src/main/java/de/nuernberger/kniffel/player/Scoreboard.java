package de.nuernberger.kniffel.player;

public class Scoreboard {
    private final int[] scoreboard;
    private final boolean[] scoreSet;

    private boolean isKniffelCrossedOut;

    public Scoreboard(){
        this.isKniffelCrossedOut = false;
        this.scoreboard = new int[18];
        this.scoreSet = new boolean[scoreboard.length];
    }

    public int getScoreAtIndex(int index){
        return scoreboard[index];
    }
    public void setScoreAtIndex(int index,int value){
        this.scoreboard[index] = value;
        this.scoreSet[index] = true;
    }
    public void setKniffelBonus(){
            this.scoreboard[13] += 25;
    }
    public boolean isKniffelCrossedOut(){
        return isKniffelCrossedOut;
    }
    public void crossOutKniffel(){
        isKniffelCrossedOut = true;
    }
    public int[] getScoreboard() {
        return scoreboard;
    }
    public boolean isScoreSetAt(int index){
        return scoreSet[index];
    }
    public boolean[] getScoreSet(){
        return scoreSet;
    }
}
