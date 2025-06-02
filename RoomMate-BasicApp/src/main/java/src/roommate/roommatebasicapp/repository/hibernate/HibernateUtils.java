package src.roommate.roommatebasicapp.repository.hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.domain.RoomsMembers;
import src.roommate.roommatebasicapp.domain.User;

import javax.sound.midi.VoiceStatus;


public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            sessionFactory=createNewSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory(){
        sessionFactory=new Configuration()
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(RoomsMembers.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static void closeSessionFactory(){
        if(sessionFactory!=null){
            sessionFactory.close();
        }
    }
}
