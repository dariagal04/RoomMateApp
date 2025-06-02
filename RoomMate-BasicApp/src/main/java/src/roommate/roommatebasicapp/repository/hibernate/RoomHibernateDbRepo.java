package src.roommate.roommatebasicapp.repository.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import src.roommate.roommatebasicapp.domain.*;
import src.roommate.roommatebasicapp.repository.Interfaces.IRoomDbRepo;

import java.util.List;

public class RoomHibernateDbRepo implements IRoomDbRepo {

    private static final Logger logger = LogManager.getLogger(RoomHibernateDbRepo.class);

    public RoomHibernateDbRepo() {
        logger.info("Initializing RoomHibernateDbRepo");
    }

    @Override
    public List<Room> getAll() {
        logger.traceEntry("Getting all rooms");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Room> rooms = session.createQuery("from Room", Room.class).list();
            logger.traceExit(rooms);
            return rooms;
        } catch (Exception e) {
            logger.error("Error getting all rooms: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Room getOne(String roomCode) {
        logger.traceEntry("Getting room with code {}", roomCode);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Room room = session.get(Room.class, roomCode);
            logger.traceExit(room);
            return room;
        } catch (Exception e) {
            logger.error("Error getting room {}: {}", roomCode, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean saveEntity(Room entity) {
        logger.traceEntry("Saving room {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(entity);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error saving room: {}", e.getMessage(), e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean deleteEntity(String code) {
        logger.traceEntry("Deleting room with code {}", code);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Room room = session.get(Room.class, code);
            if (room == null) {
                logger.warn("Room with code {} not found for delete", code);
                return false;
            }
            tx = session.beginTransaction();
            session.delete(room);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting room {}: {}", code, e.getMessage(), e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean updateEntity(Room entity) {
        logger.traceEntry("Updating room {}", entity);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error updating room: {}", e.getMessage(), e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    // Save method for RoomsMembers, similar to saveEntity
    public boolean saveEntityRM(RoomsMembers rm) {
        logger.traceEntry("Saving RoomsMembers {}", rm);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(rm);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error saving RoomsMembers: {}", e.getMessage(), e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public Iterable<RoomsMembers> findByUsername(String username) {
        return null;
    }

    @Override
    public boolean saveExpense(Expenses expense) {
        return false;
    }

    @Override
    public List<Expenses> getExpenses(String code) {
        return List.of();
    }

    @Override
    public List<Chores> getChores(String roomCode) {
        return List.of();
    }

    @Override
    public void addChore(String roomCode, Chores chore) {

    }

    @Override
    public void updateChore(Chores chore) {

    }

    @Override
    public List<User> findUsersByRoom(String id) {
        return List.of();
    }
}
