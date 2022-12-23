package de.nuernberger.kniffel.player;

public class Dice {

    private boolean locked;

    private int score;

    public Dice(){
        this.locked = false;
        this.score = 0;
    }

    public boolean isLocked() {
        return locked;
    }

    public void lock(boolean locked) {
        this.locked = locked;
    }

    public int getScore() {
        return score;
    }
    public void shuffle(){
        this.score = (int) (Math.random()*6+1);
    }

}
