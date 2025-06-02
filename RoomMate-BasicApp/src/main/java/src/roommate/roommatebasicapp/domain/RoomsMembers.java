package src.roommate.roommatebasicapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import org.checkerframework.checker.units.qual.C;
import src.roommate.roommatebasicapp.domain.entity.Entity;

import java.io.Serializable;
@jakarta.persistence.Entity
@Table(name = "rooms_members")
public class RoomsMembers extends Entity<Integer> implements Serializable {

    @Column(name = "room_code",nullable = false)
    private String room_code;

    @Column(name = "member_username",nullable = false)
    private String member_username;

    @Column(name = "admin",nullable = false)
    private boolean admin;

    public RoomsMembers(String room_code, String member_username, boolean admin) {
        this.room_code = room_code;
        this.member_username = member_username;
        this.admin = admin;
    }

    public RoomsMembers() {}

    public String getRoom_code() {
        return room_code;
    }

    public void setRoom_code(String room_code) {
        this.room_code = room_code;
    }

    public String getMember_username() {
        return member_username;
    }

    public void setMember_username(String member_username) {
        this.member_username = member_username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
