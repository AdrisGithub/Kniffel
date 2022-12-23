
package de.nuernberger.kniffel;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class KniffelController implements Initializable {

    public CheckBox cbDiceOne;
    public CheckBox cbDiceTwo;
    public CheckBox cbDiceThree;
    public CheckBox cbDiceFour;
    public CheckBox cbDiceFive;
    public Button btnCountOne;
    public Button btnCountTwo;
    public Button btnCountThree;
    public Button btnCountFour;
    public Button btnCountFive;
    public Button btnThreeOfAKind;
    public Button btnFourOfAKind;
    public Button btnFullHouse;
    public Button btnSmallStraight;
    public Button btnLargeStraight;
    public Button btnKniffel;
    public Button btnJoker;
    public Label lblBonus;
    public Label lblCombinedTopWithoutBonus;
    public Label lblCombinedTopWithBonus;
    public Label lblCombinedBottom;
    public Label lblFinalScore;
    public Label lblPlayerName;
    public Label lblMessages;
    public Button btnCountSix;
    public Button btnShuffle;
    public Button btnNextPlayer;
    public ImageView imgDiceOne;
    public ImageView imgDiceTwo;
    public ImageView imgDiceThree;
    public ImageView imgDiceFour;
    public ImageView imgDiceFive;
    public ImageView[] dices;
    public MediaPlayer media;
    public KniffelPort kniffelPort;

    public void setCountOne() {
        kniffelPort.setTextOnTopButtons(1,btnCountOne,lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus);
    }
    public void setCountTwo() {
        kniffelPort.setTextOnTopButtons(2,btnCountTwo,lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus);
    }
    public void setCountThree() {
        kniffelPort.setTextOnTopButtons(3,btnCountThree,lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus);
    }
    public void setCountFour () {
        kniffelPort.setTextOnTopButtons(4,btnCountFour,lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus);
    }
    public void setCountFive () {
        kniffelPort.setTextOnTopButtons(5,btnCountFive,lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus);
    }
    public void setCountSix() {
        kniffelPort.setTextOnTopButtons(6,btnCountSix,lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus);
    }
    public void setThreeOfAKind() {
        kniffelPort.setTextThreeFourOfAKind(3,btnThreeOfAKind);
    }

    public void setFourOfAKind() {
        kniffelPort.setTextThreeFourOfAKind(4,btnFourOfAKind);
    }

    public void setFullHouse() {
        kniffelPort.setFullHouse(btnFullHouse);
    }

    public void setSmallStraight() {
        kniffelPort.setSmallStraight(btnSmallStraight);
    }

    public void setLargeStraight() {
        kniffelPort.setBigStraight(btnLargeStraight);
    }

    public void setKniffel() {
        kniffelPort.setKniffel(btnKniffel);
    }

    public void setJoker() {
        kniffelPort.setJoker(btnJoker);
    }

    public void shuffle() {
        kniffelPort.shuffleRotation(btnShuffle,dices,media);
    }

    public void lockDiceFive() {
        kniffelPort.setDice(5,cbDiceFive);
    }

    public void lockDiceFour() {
        kniffelPort.setDice(4,cbDiceFour);
    }

    public void lockDiceThree() {
        kniffelPort.setDice(3,cbDiceThree);
    }

    public void lockDiceTwo() {
        kniffelPort.setDice(2,cbDiceTwo);
    }

    public void lockDiceOne() {
        kniffelPort.setDice(1,cbDiceOne);
    }

    public void nextPlayerTurn() {
        kniffelPort.switchPlayer(lblCombinedTopWithoutBonus,lblBonus,lblCombinedTopWithBonus,lblPlayerName,btnShuffle,dices);
        kniffelPort.resetCheckBoxes(cbDiceOne,cbDiceTwo,cbDiceThree,cbDiceFour,cbDiceFive);
        kniffelPort.updateAllButtons(btnCountOne,btnCountTwo,btnCountThree,btnCountFour,btnCountFive,btnCountSix,btnThreeOfAKind,btnFourOfAKind,btnFullHouse,btnSmallStraight,btnLargeStraight,btnKniffel,btnJoker);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kniffelPort = new KniffelPort(lblMessages,lblCombinedBottom,lblFinalScore);
        media = new MediaPlayer(new Media( new File("src/main/resources/de/nuernberger/kniffel/dice_shuffle.wav").toURI().toString()));
        dices = new ImageView[]{imgDiceOne, imgDiceTwo, imgDiceThree, imgDiceFour, imgDiceFive};
        kniffelPort.resetDiceImages(dices);
        lblPlayerName.setText("1/"+kniffelPort.getPlayersLength());
    }

}