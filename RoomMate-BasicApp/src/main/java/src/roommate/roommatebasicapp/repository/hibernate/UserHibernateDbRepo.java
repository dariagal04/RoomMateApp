package src.roommate.roommatebasicapp.repository.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.repository.Interfaces.IUserDbRepo;

import java.util.List;

public class UserHibernateDbRepo implements IUserDbRepo {

    private static final Logger logger = LogManager.getLogger(UserHibernateDbRepo.class);

    public UserHibernateDbRepo() {
        logger.info("Initializing UserHibernateDbRepo");
    }

    @Override
    public List<User> getAll() {
        logger.traceEntry("Getting all users");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            logger.traceExit(users);
            return users;
        } catch (Exception e) {
            logger.error("Error getting all users", e);
            return null;
        }
    }

    @Override
    public User getOne(String username) {
        logger.traceEntry("Getting user by username {}", username);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            logger.traceExit(user);
            return user;
        } catch (Exception e) {
            logger.error("Error getting user by username: {}", username, e);
            return null;
        }
    }

    @Override
    public boolean saveEntity(User user) {
        logger.traceEntry("Saving user {}", user);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error saving user", e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean deleteEntity(String username) {
        logger.traceEntry("Deleting user by username {}", username);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            User user = session.get(User.class, username);
            if (user == null) {
                logger.warn("User not found for delete: {}", username);
                return false;
            }
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error deleting user", e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public boolean updateEntity(User user) {
        logger.traceEntry("Updating user {}", user);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            logger.traceExit(true);
            return true;
        } catch (Exception e) {
            logger.error("Error updating user", e);
            if (tx != null) tx.rollback();
            return false;
        }
    }

    // Dacă nu trebuie în IUserDbRepo, poți elimina această metodă
    @Override
    public boolean saveEntityRM(src.roommate.roommatebasicapp.domain.RoomsMembers roomsMembers) {
        logger.warn("saveEntityRM not implemented");
        return false;
    }

    @Override
    public void addScore(String username, int score) {

    }

    @Override
    public int getUserScore(String username) {
        return 0;
    }
}
