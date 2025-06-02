package src.roommate.roommatebasicapp.domain;

import src.roommate.roommatebasicapp.domain.entity.Entity;

import java.io.Serializable;

public class Chores  extends Entity<Integer> implements Serializable {

    String choreName;
    ChoreStatus status;
    String roomCode;
    public Chores(String choreName, ChoreStatus status, String roomCode) {
        this.choreName = choreName;
        this.status = status;
        this.roomCode = roomCode;
    }


    public String getChoreName() {
        return choreName;
    }

    public void setChoreName(String choreName) {
        this.choreName = choreName;
    }

    public ChoreStatus getStatus() {
        return status;
    }

    public void setStatus(ChoreStatus status) {
        this.status = status;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Override
    public String toString() {
        return "Chores{" +
                ", choreName='" + choreName + '\'' +
                ", status=" + status +
                ", roomCode='" + roomCode + '\'' +
                '}';
    }
}
