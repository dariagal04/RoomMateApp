package src.roommate.roommatebasicapp.domain.entity;
import jakarta.persistence.*;

import java.io.Serializable;
@MappedSuperclass
public abstract class Entity<IdType> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private IdType id;
    public Entity() {}
    public Entity(IdType id) {
        this.id = id;
    }
    public IdType getId() {
        return id;
    }
    public void setId(IdType id) {
        this.id = id;
    }
}