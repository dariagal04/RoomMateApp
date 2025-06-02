package src.roommate.roommatebasicapp;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.domain.RoomsMembers;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.domain.validators.ValidationException;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountController {

    UserService userService;
    RoomService roomService;
    User loggedInUser;
    Stage stage;

    @FXML
    private TableView<Room> roomsTable;

    @FXML
    private TableColumn<Room, String> roomCodeColumn;

    @FXML
    private TableColumn<Room, String> roomNameColumn;


    private static final Logger logger = LogManager.getLogger();

    public void setService(UserService userService, RoomService roomService, Stage stage) {
        this.userService = userService;
        this.roomService = roomService;
        this.stage = stage;
    }

    @FXML
    private Label usernameLabel;

    @FXML
    private Button createRoomButton;

    @FXML
    private Button joinRoomButton;

    @FXML
    private Button closeButton;
    @FXML
    private TextField roomCodeTextField;

    public void setUser(User user){
        this.loggedInUser = user;

        if(loggedInUser != null){
            User user1= this.loggedInUser;

            System.out.println("Logged in user:"+ loggedInUser.getUsername());

            int score = userService.getUserScore(loggedInUser.getUsername());
            usernameLabel.setText(loggedInUser.getNickname() + " (Score: " + score + ")");
            loadUserRooms();
        }else{
            usernameLabel.setText("No user logged in");
            System.out.println("No user logged in");
        }
    }

    @FXML
    public void initialize() {
        roomCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        roomsTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // dublu click
                Room selectedRoom = roomsTable.getSelectionModel().getSelectedItem();
                if (selectedRoom != null) {
                    openDashboardRoom(selectedRoom);
                }
            }
        });

    }

    @FXML
    public void onCreateRoomButtonClick() throws IOException {
        openDashboardCreateRoom();

    }



    private void openDashboardCreateRoom() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("create-room-view.fxml"));
            AnchorPane root = loader.load();


            Stage stage = new Stage();
            stage.setTitle("Room");
            stage.setScene(new Scene(root));
            stage.show();

            CreateRoomController createRoomController= loader.getController();
            createRoomController.setService(userService,roomService,stage);
            createRoomController.setUser(loggedInUser);


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading dashboard: " + e.getMessage());
        }
    }


    @FXML
    public void onJoinRoomButtonClick() throws IOException {
        String roomCode = roomCodeTextField.getText().trim();


        if (roomCode.isEmpty() ) {
            System.out.println("Room's code cannot be empty.");
            return;
        }

        try {

            Room room = roomService.getOne(roomCode);
            User loggedInUser = this.loggedInUser;
            RoomsMembers roomsMembers = new RoomsMembers(roomCode,loggedInUser.getUsername(),false);


            boolean alreadyMember = false;
            for (RoomsMembers rm : roomService.getRoomsForUser(loggedInUser.getUsername())) {
                if (rm.getRoom_code().equals(roomCode)) {
                    alreadyMember = true;
                    break;
                }
            }

            if (alreadyMember) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Already a member");
                alert.setHeaderText(null);
                alert.setContentText("You are already a member of this room.");
                alert.showAndWait();
                return;
            }

            if (room != null) {
                roomService.saveEntityRM(roomsMembers);
                System.out.println("Room joined successfully");
                logger.info("Room joined successfully");


                roomCodeTextField.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Congrats!");
                alert.setContentText("Room joined successfully");
                alert.showAndWait();

                stage.close();

            } else {
                System.out.println("Invalid room code.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid room code");
                alert.setHeaderText("Invalid room code");
                alert.setContentText("Invalid room code");
                alert.showAndWait();
            }
        } catch (ValidationException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }

    }

    private void loadUserRooms() {
        try {
            Iterable<RoomsMembers> userRooms = roomService.getRoomsForUser(loggedInUser.getUsername());

            ObservableList<Room> rooms = FXCollections.observableArrayList();


            for (RoomsMembers rm : userRooms) {
                System.out.println("RoomMember found: " + rm.getRoom_code());
                Room room = roomService.getOne(rm.getRoom_code());
                if (room != null) {
                    rooms.add(room);
                }
            }

            //roomCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
            //roomNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            roomsTable.setItems(rooms);

        } catch (Exception e) {
            logger.error("Failed to load rooms for user", e);
        }
    }


    private void openDashboardRoom(Room room) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("room-view.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Room: " + room.getName());
            stage.setScene(new Scene(root));
            stage.show();

            RoomController roomController = loader.getController();
            roomController.setService(userService, roomService, stage);
            roomController.setUser(loggedInUser);
            roomController.setRoom(room); // Transmite și camera selectată

        } catch (IOException e) {
            logger.error("Error opening room view", e);
        }
    }

    @FXML
    private void onCloseButtonClick() {
        stage.close();
    }

}


