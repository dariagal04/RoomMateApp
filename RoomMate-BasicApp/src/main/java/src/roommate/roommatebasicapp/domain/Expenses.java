package src.roommate.roommatebasicapp.domain;

import src.roommate.roommatebasicapp.domain.entity.Entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Expenses extends Entity<Integer> implements Serializable {

    String username;
    String expenseType;
    Float amount;
    String description;
    LocalDateTime dueDate;
    String roomCode;

    public Expenses(String username, String expenseType, Float amount, String description, LocalDateTime dueDate, String roomCode) {
        this.username = username;
        this.expenseType = expenseType;
        this.amount = amount;
        this.description = description;
        this.dueDate = dueDate;
        this.roomCode = roomCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getRoomCode() {
        return roomCode;
    }
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Override
    public String toString() {
        return "Expenses{" +
                " Expense dded by '" + username + '\'' +
                ", expenseType='" + expenseType + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", roomCode='" + roomCode + '\'' +
                '}';
    }
}
