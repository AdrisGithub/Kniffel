package de.nuernberger.kniffel;

import de.nuernberger.kniffel.player.Player;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class KniffelValidator {

    private final Label errorMessage;
    private int sumOfAllDices;
    private int sumOfThisNumber;

    public KniffelValidator(Label errorMessage){
        this.errorMessage = errorMessage;
        this.sumOfAllDices = 0;
    }

    public boolean isFullHouse(Player currentPlayer){
        HashMap<Integer,Integer> currentRoll = initHashmap(currentPlayer);
        if(currentRoll.size() == 2 && currentRoll.containsValue(3) && currentRoll.containsValue(2)){
            errorMessage.setText("You assigned the value 25");
            return true;
        }
        errorMessage.setText("You can't set here");
        return false;
    }
    public boolean isSettingKniffelPossible(boolean setAButton, Player currentPlayer){
        sumOfAllDices = currentPlayer.sumOfAllDices();
        if(sumOfAllDices != 0 && !setAButton && !currentPlayer.isKniffelCrossedOut()){
            return true;
        }
        errorMessage.setText("You can't set here");
        return false;
    }
    public boolean isThisAKniffel(Player currentPlayer){
        HashMap<Integer, Integer> currentRoll = initHashmap(currentPlayer);
        if(currentRoll.size() == 1){
            return true;
        }
        errorMessage.setText("You can't set here");
        return false;
    }
    public boolean isSmallStraightPossible(Player currentPlayer) {
        int[] sortedArray = currentPlayer.getDiceValues();
        Arrays.sort(sortedArray);
        HashSet<Integer> sortHash = new HashSet<>();
        for (Integer k : sortedArray) {
            sortHash.add(k);
        }
        int y = 0;
        for (Integer j : sortHash) {
            sortedArray[y] = j;
            y++;
        }
        sortedArray[4] = 0;
        for (int i = 0; i < 2; i++) {
            for (int o=0;o<3;o++){
                if(sortedArray[o]+1 != sortedArray[o+1]){
                    errorMessage.setText("You can't set here");
                    return false;
                }
            }
        }
        errorMessage.setText("You assigned the value 30");
        return true;
    }
    public boolean isBigStraightPossible(Player currentPlayer){
        int[] sortedArray = currentPlayer.getDiceValues();
        Arrays.sort(sortedArray);
        for (int i=0;i<4;i++){
            if(sortedArray[i]+1 != sortedArray[i+1]){
                errorMessage.setText("You can't set here");
                return false;
            }
        }
        errorMessage.setText("You assigned the value 40");
        return true;
    }
    public boolean isSetPossible(int index,boolean setAButton,Player currentPlayer){
        sumOfAllDices = currentPlayer.sumOfAllDices();
        if(sumOfAllDices != 0 && !setAButton && !currentPlayer.isValueSetScore(index)){
            return true;
        }
        errorMessage.setText("You can't set here");
        return false;
    }
    public boolean containsThisNumber(int number,Player currentPlayer,boolean setAButton){
        sumOfThisNumber = currentPlayer.countNumbersInCurrentRoll(number);
        if(sumOfThisNumber!=0 && !setAButton && !currentPlayer.isValueSetScore(number - 1)){
            errorMessage.setText("You assigned the value " + sumOfThisNumber);
            return true;
        }
        errorMessage.setText("You can't set here");
        return false;
    }
    public int getSumOfThisNumber() {
        return sumOfThisNumber;
    }
    public boolean canBeLocked(int currentMove, CheckBox checkBox){
        if(currentMove == 3){
            checkBox.setSelected(false);
            errorMessage.setText("You can't lock a dice without a value");
            return false;
        }
        return true;
    }
    public boolean canBeCrossed(int index, int currentMove, Player currentPlayer, boolean setAButton){
        if(currentMove == 0 && !currentPlayer.isValueSetScore(index) && !setAButton){
            errorMessage.setText("You crossed out this Button");
            return true;
        }
        errorMessage.setText("You can't cross out this Button");
        return false;
    }
    public boolean isThreeFourOfAKindPossible(int amount,Player currentPlayer){
        HashMap<Integer, Integer> currentRoll = initHashmap(currentPlayer);
        for (int o : currentRoll.keySet()) {
            if (currentRoll.get(o) >= amount) {
                errorMessage.setText(">ou assigned the Value "+sumOfAllDices);
                return true;
            }
        }
        errorMessage.setText("You can't set here");
        return false;
    }
    public boolean canBeShuffled(Player currentPlayer,int currentMove,boolean setAButton,boolean isCurrentlyShuffling){
        if(currentMove>0 && !setAButton && !isCurrentlyShuffling && !currentPlayer.checkIfAllAreLocked()){
            return true;
        }else{
            if (currentPlayer.checkIfAllAreLocked()) {
                errorMessage.setText("Every dice is locked you can't shuffle ");
                return false;
            }
            errorMessage.setText("You can't shuffle");
            return false;
        }
    }
    private HashMap<Integer, Integer> initHashmap(Player currentPlayer){
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for (int i:currentPlayer.getDiceValues()) {
            if(hashMap.containsKey(i)){
                hashMap.put(i,hashMap.get(i)+1);
            }else{
                hashMap.put(i,1);
            }
        }
        return hashMap;
    }

    public int getSumOfAllDices() {
        return sumOfAllDices;
    }
}
