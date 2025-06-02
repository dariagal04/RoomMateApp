package src.roommate.roommatebasicapp.domain;

import src.roommate.roommatebasicapp.domain.entity.Entity;

import java.io.Serializable;

public class ScorePoints extends Entity<Integer> implements Serializable {

    String username;
    int score;

    public ScorePoints(String username, int score) {
        this.username = username;
        this.score = score;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

}
