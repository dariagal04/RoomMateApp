package src.roommate.roommatebasicapp.service;

import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.domain.entity.Entity;
import src.roommate.roommatebasicapp.repository.DbRepo.UserDbRepo;
import src.roommate.roommatebasicapp.repository.Interfaces.IUserDbRepo;

import java.util.List;

public class UserService implements IService {

    IUserDbRepo userDbRepo;

    public UserService(IUserDbRepo userDbRepo) {
        this.userDbRepo = userDbRepo;
    }

    public boolean saveEntity(User user) {
        return userDbRepo.saveEntity(user);
    }

    public boolean deleteEntity(String username){
        return userDbRepo.deleteEntity(username);
    }


    public User authenticate(String username, String password) {
        User user = userDbRepo.getOne(username);
        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }




}
