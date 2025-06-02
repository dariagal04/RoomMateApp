package src.roommate.roommatebasicapp.repository;

import src.roommate.roommatebasicapp.domain.RoomsMembers;

import java.util.List;
import java.util.Optional;

public interface Repository<EntityType,EntityId> {
    List<EntityType> getAll();

    EntityType getOne(String string);

    boolean saveEntity(EntityType entity);

    boolean deleteEntity(String string);

    boolean updateEntity(EntityType entity);

    boolean saveEntityRM(RoomsMembers roomsMembers);
}