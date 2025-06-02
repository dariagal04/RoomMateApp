package src.roommate.roommatebasicapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;
import src.roommate.roommatebasicapp.domain.validators.ValidationException;


import java.io.IOException;

public class LoginController {

    private UserService userService;
    private RoomService roomService;
    private static final Logger logger = LogManager.getLogger();


    public void setService(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    public void onLoginButtonClick() throws IOException {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("username and password cannot be empty.");
            return;
        }

        try {
            User user = userService.authenticate(username, password);

            if (user != null) {
                System.out.println("Login successful");
                logger.info("Login successful");
                openDashboard(user);

                //initModel();
                usernameTextField.clear();
                passwordTextField.clear();
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

    private void openDashboard(User user) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("user-view.fxml"));
            AnchorPane root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Account");
            stage.setScene(new Scene(root));
            stage.show();

            AccountController accountController= loader.getController();
            accountController.setService(userService,roomService,stage);
            accountController.setUser(user);



        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard: " + e.getMessage());
        }
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        openDashboardRegister();

    }

    private void openDashboardRegister() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register-view.fxml"));
            AnchorPane root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Register");
            stage.setScene(new Scene(root));
            stage.show();

            RegisterController registerController= loader.getController();
            registerController.setService(userService,roomService,stage);





        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard: " + e.getMessage());
        }
    }





}