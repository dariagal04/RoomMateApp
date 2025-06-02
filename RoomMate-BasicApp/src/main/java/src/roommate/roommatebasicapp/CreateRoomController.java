package src.roommate.roommatebasicapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.domain.RoomsMembers;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.domain.validators.ValidationException;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;

import java.security.SecureRandom;

public class CreateRoomController {

    UserService userService;
    RoomService roomService;
    User loggedInUser;
    Stage stage;
    private static final Logger logger = LogManager.getLogger();


    public void setService(UserService userService, RoomService roomService, Stage stage) {
        this.userService = userService;
        this.roomService = roomService;
        this.stage = stage;
    }
    public void setUser(User user){
        this.loggedInUser = user;

        if(loggedInUser != null){
            User user1= this.loggedInUser;

            System.out.println("Logged in user:"+ loggedInUser.getUsername());

        }else{
            System.out.println("No user logged in");
        }
    }


    @FXML
    private TextField roomNameTextField;

    @FXML
    private Button createRoomButton;



    public static String generateCode(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
       SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    @FXML
    public void onCreateRoomButtonClick(){

        String roomName = roomNameTextField.getText().trim();
        String code = generateCode(6);
        int memberNr = 1;

        if (roomName.isEmpty() ) {
            System.out.println("Room's name cannot be empty.");
            return;
        }

        try {
            Room room = new Room(code, memberNr, roomName);

            User loggedInUser = this.loggedInUser;
            RoomsMembers roomsMembers = new RoomsMembers(code,loggedInUser.getUsername(),true);


            if (room != null) {
                roomService.saveEntity(room);
                roomService.saveEntityRM(roomsMembers);
                System.out.println("Room created successfully");
                logger.info("Room created successfully");


                roomNameTextField.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Congrats!");
                alert.setContentText("Room created successfully");
                alert.showAndWait();

                stage.close();

            } else {
                System.out.println("Invalid room name.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid room name");
                alert.setHeaderText("Invalid room name");
                alert.setContentText("Invalid room name");
                alert.showAndWait();
            }
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

}
