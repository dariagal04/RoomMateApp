package src.roommate.roommatebasicapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import src.roommate.roommatebasicapp.domain.*;
import src.roommate.roommatebasicapp.service.RoomService;
import src.roommate.roommatebasicapp.service.UserService;

public class ChoresController {

    @FXML
    TableView<Chores> choresTable;
    @FXML
    TableColumn<Chores, String> choreNameColumn;
    @FXML
    TableColumn<Chores, ChoreStatus> statusColumn;

    @FXML
    TextField choreNameField;
    @FXML
    Button addChoreButton;

    @FXML
    Button markInProcessButton;
    @FXML
    Button markCompletedButton;


    private RoomService roomService;
    private UserService userService;
    private User currentUser;
    private Room room;

    private ObservableList<Chores> model = FXCollections.observableArrayList();

    public void setService(RoomService roomService, UserService userService, User currentUser, Room room) {
        this.roomService = roomService;
        this.userService = userService;
        this.currentUser = currentUser;
        this.room = room;
        loadChores();
    }

    private void loadChores() {
        model.setAll(roomService.getChores(room.getCode())); // metoda de adăugat în service
    }

    @FXML
    public void initialize() {
        choreNameColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getChoreName()));
        statusColumn.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getStatus()));

        choresTable.setItems(model);

        choresTable.setRowFactory(tv -> {
            TableRow<Chores> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Chores chore = row.getItem();
                    if (chore.getStatus() != ChoreStatus.COMPLETED) {
                        chore.setStatus(ChoreStatus.COMPLETED);
                        roomService.updateChore(chore);
                        userService.addScore(currentUser.getUsername(), 5);
                        loadChores();
                    }
                }
            });
            return row;
        });
    }

    @FXML
    public void onAddChoreButtonClick() {
        String name = choreNameField.getText();
        if (name == null || name.isEmpty()) return;

        Chores newChore = new Chores(name, ChoreStatus.NOT_COMPLETED, room.getCode());
        roomService.addChore(room.getCode(), newChore);
        choreNameField.clear();
        loadChores();
    }


    @FXML
    public void onMarkInProcessButtonClick() {
        Chores selected = choresTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (selected.getStatus() != ChoreStatus.IN_PROCESS) {
            selected.setStatus(ChoreStatus.IN_PROCESS);
            roomService.updateChore(selected);
            loadChores();
        }
    }

    @FXML
    public void onMarkCompletedButtonClick() {
        Chores selected = choresTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        if (selected.getStatus() != ChoreStatus.COMPLETED) {
            selected.setStatus(ChoreStatus.COMPLETED);
            roomService.updateChore(selected);
            userService.addScore(currentUser.getUsername(), 5);

            loadChores();
        }

    }



}
