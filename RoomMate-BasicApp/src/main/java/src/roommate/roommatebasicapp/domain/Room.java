package src.roommate.roommatebasicapp.domain;

import org.checkerframework.checker.units.qual.C;
import src.roommate.roommatebasicapp.domain.entity.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;

@jakarta.persistence.Entity
@Table(name = "rooms")
public class Room extends Entity<Integer> implements Serializable {

    @Column(name ="code",nullable = false)
    String code;
    @Column(name = "memberNr",nullable = false)
    int memberNr;

    @Column(name = "name",nullable = false)
    String name;

    //List<User> members;

    public Room(String code, int memberNr, String name){
        this.code = code;
        this.memberNr = memberNr;
        this.name = name;
    }
//    public Room(String code, int memberNr, String name, List<User> members) {
//        this.code = code;
//        this.memberNr = memberNr;
//        this.name = name;
//        this.members = members;
//    }

    public Room() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMemberNr() {
        return memberNr;
    }

    public void setMemberNr(int memberNr) {
        this.memberNr = memberNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<User> getMembers() {
//        return members;
//    }
//    public void setMembers(List<User> members) {
//        this.members = members;
//    }

    @Override
    public String toString() {
        return "Room{" +
                "code='" + code + '\'' +
                ", memberNr=" + memberNr +
                ", name='" + name + '\'' +
                '}';
    }
}
