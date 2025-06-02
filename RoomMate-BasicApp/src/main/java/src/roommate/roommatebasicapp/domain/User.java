package src.roommate.roommatebasicapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import src.roommate.roommatebasicapp.domain.entity.Entity;

import java.io.Serializable;
import java.util.List;
@jakarta.persistence.Entity
@Table(name = "users")
public class User extends Entity<Integer> implements Serializable {

    @Column(name = "username",nullable = false)
    String username;

    @Column(name = "password",nullable = false)
    String password;

    @Column(name = "nickname",nullable = false)
    String nickname;
    //List<Room> rooms;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

//    public User(String username, String password, String nickname, List<Room> rooms) {
//        this.username = username;
//        this.password = password;
//        this.nickname = nickname;
//        this.rooms = rooms;
//    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

//    public List<Room> getRooms() {
//        return rooms;
//    }
//    public void setRooms(List<Room> rooms) {
//        this.rooms = rooms;
//    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
