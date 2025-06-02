package src.roommate.roommatebasicapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.domain.validators.ValidationException;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;

public class RegisterController {

    UserService userService;
    RoomService roomService;
    Stage stage;
    private static final Logger logger = LogManager.getLogger();


    public void setService(UserService userService, RoomService roomService, Stage stage) {
        this.userService = userService;
        this.roomService = roomService;
        this.stage = stage;
    }


    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField nicknameTextField;

    @FXML
    private Button createAccountButton;


    @FXML
    public void onCreateAccountButtonClick(){
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        String nickname = nicknameTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || nickname.isEmpty()) {
            System.out.println("username, password and nickname cannot be empty.");
            return;
        }

        try {
            User user = new User(username, password, nickname);

            if (user != null) {
                userService.saveEntity(user);
                System.out.println("Account created successfully");
                logger.info("Account created successfully");


                usernameTextField.clear();
                passwordTextField.clear();
                nicknameTextField.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Congrats!");
                alert.setContentText("Account created successfully");
                alert.showAndWait();

                stage.close();

            } else {
                System.out.println("Invalid username or password.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid username or password");
                alert.setHeaderText("Invalid username or password");
                alert.setContentText("Invalid username or password");
                alert.showAndWait();
            }
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

}
