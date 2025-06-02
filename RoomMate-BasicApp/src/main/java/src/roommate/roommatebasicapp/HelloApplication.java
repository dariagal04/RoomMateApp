package src.roommate.roommatebasicapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.ImmutableEntityUpdateQueryHandlingMode;
import src.roommate.roommatebasicapp.repository.DbRepo.RoomDbRepo;
import src.roommate.roommatebasicapp.repository.DbRepo.UserDbRepo;
import src.roommate.roommatebasicapp.repository.Interfaces.IRoomDbRepo;
import src.roommate.roommatebasicapp.repository.Interfaces.IUserDbRepo;
import src.roommate.roommatebasicapp.repository.hibernate.RoomHibernateDbRepo;
import src.roommate.roommatebasicapp.repository.hibernate.UserHibernateDbRepo;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HelloApplication extends Application {


    private UserService userService;
    private RoomService roomService;
    private static final Logger logger = LogManager.getLogger();


    @Override
    public void start(Stage stage) throws IOException {

        Properties props = new Properties();
//        try {
//            props.load(new FileReader("/bd.config"));
//        } catch (IOException e) {
//            System.out.println("Cannot find bd.config " + e);
//            logger.error(e);
//        }
        try (InputStream input = HelloApplication.class.getResourceAsStream("/bd.config")) {
            if (input == null) {
                System.out.println("Nu s-a găsit bd.config în resurse!");
                return;
            }
            props.load(input);
        } catch (IOException e) {
            System.out.println("Eroare la încărcarea fișierului bd.config: " + e.getMessage());
        }


        IUserDbRepo userDbRepo = new UserDbRepo(props);
        IRoomDbRepo roomDbRepo = new RoomDbRepo(props);

        IUserDbRepo userHibernateDbRepo = new UserHibernateDbRepo();
        IRoomDbRepo roomHibernateDbRepo = new RoomHibernateDbRepo();
        this.roomService = new RoomService(roomDbRepo);
        this.userService = new UserService(userDbRepo);




        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        LoginController controller = fxmlLoader.getController();
        controller.setService(userService, roomService);
        stage.setTitle("LOGIN");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}