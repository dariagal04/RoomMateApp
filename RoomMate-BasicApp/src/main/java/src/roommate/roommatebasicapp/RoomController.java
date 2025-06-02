package src.roommate.roommatebasicapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;

import java.util.List;


public class RoomController {
    UserService userService;
    RoomService roomService;
    User loggedInUser;
    Stage stage;
    Room room;
    public void setService(UserService userService, RoomService roomService, Stage stage) {
        this.userService = userService;
        this.roomService = roomService;
        this.stage = stage;

        //loadUsersInRoom(room);

    }

    public void setUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    public void setRoom(Room room) {
        this.room = room;
        System.out.println("Opened room: " + room.getCode() + " - " + room.getName());

        loadUsersInRoom(room);

    }


    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> nicknameColumn;

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nicknameColumn.setCellValueFactory(new PropertyValueFactory<>("nickname"));
    }


    public void loadUsersInRoom(Room room) {
        List<User> users = roomService.getUsersInRoom(room);
        System.out.println("Users loaded for room: " + users);

        usersTable.getItems().setAll(users);
    }


    public void onCloseButtonClick(ActionEvent actionEvent) {
        stage.close();
    }

    public void onAddExpenseButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-expense-view.fxml"));
            Parent root = loader.load();

            ExpenseController controller = loader.getController();
            controller.setService(roomService, loggedInUser.getUsername());
            controller.setRoom(room);

            Stage expenseStage = new Stage();
            expenseStage.setTitle("Add Expense");
            expenseStage.setScene(new Scene(root));
            expenseStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onChoresButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("chores-view.fxml"));
            Parent root = loader.load();

            ChoresController controller = loader.getController();
            controller.setService(roomService, userService, loggedInUser, room);

            Stage choresStage = new Stage();
            choresStage.setTitle("Chores");
            choresStage.setScene(new Scene(root));
            choresStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
