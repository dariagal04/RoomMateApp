package src.roommate.roommatebasicapp.service;

import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.domain.RoomsMembers;
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


}
