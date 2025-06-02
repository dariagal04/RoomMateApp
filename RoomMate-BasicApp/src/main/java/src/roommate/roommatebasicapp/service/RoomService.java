package src.roommate.roommatebasicapp.service;

import src.roommate.roommatebasicapp.domain.*;
import src.roommate.roommatebasicapp.domain.entity.Entity;
import src.roommate.roommatebasicapp.repository.DbRepo.RoomDbRepo;
import src.roommate.roommatebasicapp.repository.Interfaces.IRoomDbRepo;

import java.util.List;

public class RoomService implements IService{


    IRoomDbRepo roomDbRepo;

    public RoomService(IRoomDbRepo roomDbRepo) {
        this.roomDbRepo = roomDbRepo;
    }

    public boolean saveEntity(Room room) {
        return roomDbRepo.saveEntity(room);
    }

    public boolean deleteEntity(String code) {
        return roomDbRepo.deleteEntity(code);
    }

    public boolean saveEntityRM(RoomsMembers roomsMembers) {
        return roomDbRepo.saveEntityRM(roomsMembers);
    }

    public Room getOne(String code) {
        return roomDbRepo.getOne(code);
    }


    public Iterable<RoomsMembers> getRoomsForUser(String username) {
        return roomDbRepo.findByUsername(username);
    }

    public void addExpense(Expenses expense) {
        roomDbRepo.saveExpense(expense);
    }

    public List<Expenses> getExpensesForRoom(String code) {
        return roomDbRepo.getExpenses(code);
    }
    public List<Chores> getChores(String roomCode){
        return roomDbRepo.getChores(roomCode);
    };
    public void addChore(String roomCode, Chores chore){
        roomDbRepo.addChore(roomCode,chore);

    };
    public void updateChore(Chores chore){
        roomDbRepo.updateChore(chore);
    };

    public List<User> getUsersInRoom(Room room) {
        // Aici apelezi repo-ul care știe să găsească userii din camera respectivă
        return roomDbRepo.findUsersByRoom(room.getCode());
    }

}
