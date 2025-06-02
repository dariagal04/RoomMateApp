package src.roommate.roommatebasicapp.repository.Interfaces;

import src.roommate.roommatebasicapp.domain.*;
import src.roommate.roommatebasicapp.repository.Repository;

import java.util.List;

public interface IRoomDbRepo extends Repository<Room, Integer> {
    Iterable<RoomsMembers> findByUsername(String username);

    boolean saveExpense(Expenses expense);

    List<Expenses> getExpenses(String code);

    List<Chores> getChores(String roomCode);

    void addChore(String roomCode, Chores chore);

    void updateChore(Chores chore);

    List<User> findUsersByRoom(String roomCode);
}
