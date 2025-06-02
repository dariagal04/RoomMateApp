package src.roommate.roommatebasicapp.repository.Interfaces;


import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.repository.Repository;

public interface IUserDbRepo extends Repository<User, Integer> {
    void addScore(String username, int score);

    int getUserScore(String username);
}
