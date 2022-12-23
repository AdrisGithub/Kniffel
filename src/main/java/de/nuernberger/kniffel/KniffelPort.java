package de.nuernberger.kniffel;

import de.nuernberger.kniffel.player.Player;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.*;

public class KniffelPort {
    private static Player[] players = new Player[0];
    private int currentPlayer;
    private int moves;
    private boolean setAButton;
    private int playerIndexWhoFinished;
    private boolean isCurrentlyShuffling;
    private final Label errorMessage;
    private final Label endResult;
    private final Label bottom;
    private final KniffelValidator validator;

    public KniffelPort(Label errorMessage,Label bottom,Label endResult) {
        this.validator = new KniffelValidator(errorMessage);
        this.bottom = bottom;
        this.endResult = endResult;
        this.errorMessage = errorMessage;
        this.isCurrentlyShuffling = false;
        this.currentPlayer = 0;
        this.moves = 3;
        this.setAButton = false;
        this.playerIndexWhoFinished = -1;
    }
    public static void initPlayers(int playerAmount) {
        players = new Player[playerAmount];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
    }
    public void setDice(int dice, CheckBox checkBox) {
        if (validator.canBeLocked(moves,checkBox)) {
            players[currentPlayer].lockDice(dice - 1, !players[currentPlayer].isDiceLocked(dice - 1));
        }
    }
    public void setTextOnTopButtons(int number, Button btn, Label topWithoutBonus, Label bonus, Label topWithBonus) {
        if (validator.containsThisNumber(number,players[currentPlayer],setAButton)) {
            int sum = validator.getSumOfThisNumber();
            btn.setText(sum + "");
            players[currentPlayer].setScore(number - 1, sum);
            updateTopWithoutBonus(topWithoutBonus, bonus);
            updateTopWithBonus(topWithBonus);
            updateEndResult();
            setAButton = true;
        } else if (validator.canBeCrossed(number-1,moves,players[currentPlayer],setAButton)) {
            btn.setText("0");
            players[currentPlayer].setScore(number - 1, 0);
            updateTopWithoutBonus(topWithoutBonus, bonus);
            updateTopWithBonus(topWithBonus);
            updateEndResult();
            setAButton = true;
        }
    }
    public void shuffle(Button button, ImageView[] imageViews) throws FileNotFoundException {
        players[currentPlayer].shuffle();
        for (int i = 0; i < 5; i++) {
            imageViews[i].setImage(new Image(new FileInputStream("src/main/resources/de/nuernberger/kniffel/dice/dice" + players[currentPlayer].getDiceValue(i) + ".png")));
        }
        button.setText("Shuffle (" + --moves + ")");
        isCurrentlyShuffling = false;
    }
    private void insertValueInBottomPlayerScoreboard(int value,int index,Button btn){
        players[currentPlayer].setScore(index, value);
        btn.setText(value + "");
        updateBottom();
        setAButton = true;
    }
    public void setTextThreeFourOfAKind(int amount, Button btn) {
        if (validator.isSetPossible(amount+5,setAButton,players[currentPlayer])) {
            if (validator.isThreeFourOfAKindPossible(amount,players[currentPlayer])) {
                int sumOfAllDices = validator.getSumOfAllDices();
                insertValueInBottomPlayerScoreboard(sumOfAllDices,amount+5,btn);
            }else if (validator.canBeCrossed(amount+5,moves,players[currentPlayer],setAButton)) {
                insertValueInBottomPlayerScoreboard(KniffelConstant.CROSSEDOUT.getValue(),amount+5,btn);
            }
        }
    }
    public void setKniffel(Button btn){
        if (validator.isSettingKniffelPossible(setAButton,players[currentPlayer])) {
            if (validator.isThisAKniffel(players[currentPlayer])) {
                if(!players[currentPlayer].isValueSetScore(13)){
                    insertValueInBottomPlayerScoreboard(KniffelConstant.KNIFFEL.getValue(),13,btn);
                }else{
                    players[currentPlayer].setKniffelBonus();
                    btn.setText(players[currentPlayer].getScore(13)+"");
                    updateBottom();
                    setAButton = true;
                }
            }else if(validator.canBeCrossed(13,moves,players[currentPlayer],setAButton)){
                insertValueInBottomPlayerScoreboard(KniffelConstant.CROSSEDOUT.getValue(),13,btn);
                players[currentPlayer].crossOutKniffel();
            }
        }
    }
    public void setSmallStraight(Button btn) {
        if (validator.isSetPossible(11,setAButton,players[currentPlayer])) {
            if (validator.isSmallStraightPossible(players[currentPlayer])) {
                insertValueInBottomPlayerScoreboard(KniffelConstant.SMALLSTRAIGHT.getValue(),11,btn);
            } else if (validator.canBeCrossed(11,moves,players[currentPlayer],setAButton)) {
                insertValueInBottomPlayerScoreboard(KniffelConstant.CROSSEDOUT.getValue(),11,btn);
            }
        }
    }
    public void setBigStraight(Button btn) {
        if (validator.isSetPossible(12,setAButton,players[currentPlayer])) {
            if (validator.isBigStraightPossible(players[currentPlayer])) {
                insertValueInBottomPlayerScoreboard(KniffelConstant.BIGSTRAIGHT.getValue(),12,btn);
            } else if (validator.canBeCrossed(12,moves,players[currentPlayer],setAButton)) {
                insertValueInBottomPlayerScoreboard(KniffelConstant.CROSSEDOUT.getValue(),12,btn);
            }
        }
    }
    public void setFullHouse(Button btn){
        if (validator.isSetPossible(10,setAButton,players[currentPlayer])) {
            if (validator.isFullHouse(players[currentPlayer])) {
                insertValueInBottomPlayerScoreboard(KniffelConstant.FULLHOUSE.getValue(),10,btn);
            } else if (validator.canBeCrossed(10,moves,players[currentPlayer],setAButton)){
                insertValueInBottomPlayerScoreboard(KniffelConstant.CROSSEDOUT.getValue(),10,btn);
            }
        }
    }
    public void setJoker(Button btn){
        if(validator.isSetPossible(14,setAButton,players[currentPlayer])){
            int sum = validator.getSumOfAllDices();
            insertValueInBottomPlayerScoreboard(sum,14,btn);
        }
    }
    public void switchPlayer(Label labelTopWithoutBonus,Label bonus,Label combinedTopWithBonus,Label lblCurrentPlayer,Button btnShuffle,ImageView[] imageViews){
        if(setAButton){
            boolean wasSetRightNow = false;
            if(players[currentPlayer].isScoreboardFull() && playerIndexWhoFinished == -1){
                wasSetRightNow = true;
                this.playerIndexWhoFinished = currentPlayer;
            }
            if(currentPlayer == players.length-1){
                currentPlayer=0;
                lblCurrentPlayer.setText("1/"+players.length);
            }else{
                lblCurrentPlayer.setText(++currentPlayer+1+"/"+players.length);
            }
            if(playerIndexWhoFinished == currentPlayer&&!wasSetRightNow){
                try{
                    endGame();
                }catch(IOException io){
                    throw new RuntimeException("End Results couldn't be written in A File");
                }
            }else {
                moves = 3;
                setAButton = false;
                btnShuffle.setText("Shuffle (" + moves + ")");
                updateTopWithoutBonus(labelTopWithoutBonus, bonus);
                updateBottom();
                updateTopWithBonus(combinedTopWithBonus);
                players[currentPlayer].resetDices();
                resetDiceImages(imageViews);
                int temp = currentPlayer + 1;
                errorMessage.setText("Switched to player " + temp);
            }
        }else{
            errorMessage.setText("You must assign / cross out a value ");
        }
    }
    private void endGame() throws IOException {
        int highestScore = -1;
        int end = 0;
        int amountOfHighScorePlayers = 0;
        setAButton = false;
        PrintWriter out = new PrintWriter("src/main/resources/de/nuernberger/kniffel/gameResult/result.txt");
        out.println("The Results are:");
        for (int o=0;o<players.length;o++){
            int temp = o+1;
            out.println("Player "+temp+": "+players[o].sumOfScoreboard());
            if (players[o].sumOfScoreboard() > highestScore) {
                highestScore = players[o].sumOfScoreboard();
            }
        }
        out.println("");
        for (int i=0;i<players.length;i++){
            if(players[i].sumOfScoreboard() == highestScore){
                amountOfHighScorePlayers++;
                end = i+1;
            }
        }
        out.close();
        if(amountOfHighScorePlayers>1){
            errorMessage.setText("Game ended with a tie");
        }else{
            errorMessage.setText("Player "+end+" won!");
        }
    }
    public void updateAllButtons(Button...buttons) {
        for(int i=0;i<6;i++){
            if(!players[currentPlayer].isValueSetScore(i)){
                buttons[i].setText("Set");
            }else{
                buttons[i].setText(players[currentPlayer].getScore(i)+"");
            }
        }
        for (int o=0;o<7;o++){
            if(!players[currentPlayer].isValueSetScore(o+8)){
                buttons[o+6].setText("Set");
            }else{
                buttons[o+6].setText(players[currentPlayer].getScore(o+8)+"");
            }
        }
    }
    public void resetCheckBoxes(CheckBox ...checkBoxes){
        for (CheckBox c:checkBoxes) {
            c.setSelected(false);
        }
    }
    public void shuffleRotation(Button btnShuffle, ImageView[] dices, MediaPlayer media){
        if (validator.canBeShuffled(players[currentPlayer],moves,setAButton,isCurrentlyShuffling)) {
            isCurrentlyShuffling = true;
            RotateTransition diceRotation = new RotateTransition();
            media.stop();
            media.play();
            for (int i = 0; i < 5; i++) {
                if(!players[currentPlayer].isDiceLocked(i)){
                    diceRotation = new RotateTransition();
                    diceRotation.setByAngle(360.0);
                    diceRotation.setNode(dices[i]);
                    diceRotation.setDuration(Duration.seconds(2));
                    diceRotation.play();
                }
            }
            diceRotation.setOnFinished(event -> {try {shuffle(btnShuffle, dices);} catch (FileNotFoundException e) {throw new RuntimeException(e);}});
        }
    }
    public int getPlayersLength() {
        return players.length;
    }
    private void updateBottom(){
        int bottom = 0;
        for (int i=0;i<=6;i++){
            bottom += players[currentPlayer].getScore(i+8);
        }
        players[currentPlayer].setScore(16,bottom);
        this.bottom.setText(bottom+"");
        updateEndResult();
    }
    private void updateTopWithoutBonus(Label labelTopWithoutBonus,Label bonus){
        int topWithoutBonus = 0;
        for (int i=0;i<6;i++){
            topWithoutBonus += players[currentPlayer].getScore(i);
        }
        players[currentPlayer].setScore(6,topWithoutBonus);
        labelTopWithoutBonus.setText(topWithoutBonus+"");
        updateBonus(bonus,topWithoutBonus);
    }
    private void updateBonus(Label label,int topscore){
        if(topscore >= 63){
            players[currentPlayer].setScore(7,35);
            label.setText("35");
        }
    }
    public void resetDiceImages(ImageView[] imageViews){
        try{
            for (int i=0;i<5;i++){
                imageViews[i].setImage(new Image(new FileInputStream("src/main/resources/de/nuernberger/kniffel/dice/dice0.png")));
            }
        }catch(FileNotFoundException fn){
            throw new RuntimeException("Dice Images wurden nicht gefunden");
        }
    }
    private void updateTopWithBonus(Label label){
        int score = players[currentPlayer].getScore(6)+players[currentPlayer].getScore(7);
        players[currentPlayer].setScore(15,score);
        label.setText(score+"");
    }
    private void updateEndResult(){
        int endResult = players[currentPlayer].getScore(15)+players[currentPlayer].getScore(16);
        players[currentPlayer].setScore(17,endResult);
        this.endResult.setText(players[currentPlayer].getScore(17)+"");
    }

}
