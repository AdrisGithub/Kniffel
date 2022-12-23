package de.nuernberger.kniffel.player;

public class Player {

    private final Scoreboard scoreboard;
    private final Dice[] dices;

    public Player(){
        this.scoreboard = new Scoreboard();
        this.dices = new Dice[5];
        initDices();
    }

    private void initDices(){
        for (int i=0;i< dices.length;i++){
            dices[i] = new Dice();
        }
    }

    public void shuffle(){
        for (Dice d:dices) {
            if(!d.isLocked()){
                d.shuffle();
            }
        }
    }
    public int[] getDiceValues() {
        int[] dValues = new int[dices.length];
        for (int i=0;i< dices.length;i++){
            dValues[i] = dices[i].getScore();
        }
        return dValues;
    }
    public int getDiceValue(int index){
        return dices[index].getScore();
    }
    public void lockDice(int index,boolean state){
        dices[index].lock(state);
    }
    public boolean isDiceLocked(int index){
        return dices[index].isLocked();
    }
    public int sumOfAllDices(){
        int sum =0;
        for (Dice d:dices) {
            sum += d.getScore();
        }
        return sum;
    }
    public int getScore(int index){
        return scoreboard.getScoreAtIndex(index);
    }
    public void setScore(int index,int value){
        scoreboard.setScoreAtIndex(index,value);
    }
    public Dice[] getDices() {
        return dices;
    }
    public void resetDices(){
        for(int i=0;i<dices.length;i++){
            dices[i] = new Dice();
        }
    }
    public boolean checkIfAllAreLocked(){
        for (Dice d:getDices()) {
            if(!d.isLocked()){
                return false;
            }
        }
        return true;
    }
    public boolean isValueSetScore(int index){
        return scoreboard.isScoreSetAt(index);
    }
    public boolean isScoreboardFull(){
        for(int i=0;i<scoreboard.getScoreSet().length;i++){
            if(!(i == 6 || i== 7 || i == 8 || i== 15 || i ==16 || i ==17)){
                if(!scoreboard.getScoreSet()[i]){
                    return false;
                }
            }
        }
        return true;
    }
    public int sumOfScoreboard(){
        int i=0;
        for (int s:scoreboard.getScoreboard()) {
            i += s;
        }
        return i;
    }
    public int countNumbersInCurrentRoll(int number){
        int result = 0;
        for (int i:getDiceValues()) {
            if(i == number){
                result += number;
            }
        }
        return result;
    }
    public void setKniffelBonus(){
        scoreboard.setKniffelBonus();
    }
    public boolean isKniffelCrossedOut(){
        return scoreboard.isKniffelCrossedOut();
    }
    public void crossOutKniffel(){
        scoreboard.crossOutKniffel();
    }
}
