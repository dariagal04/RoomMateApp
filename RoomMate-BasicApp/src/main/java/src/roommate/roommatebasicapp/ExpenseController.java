package src.roommate.roommatebasicapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import src.roommate.roommatebasicapp.domain.Expenses;
import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.service.RoomService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpenseController {
    @FXML
    private TextField typeField;
    @FXML
    private TextField amountField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private TableView<Expenses> expensesTable;

    @FXML
    private TableColumn<Expenses, String> typeColumn;
    @FXML
    private TableColumn<Expenses, Float> amountColumn;
    @FXML
    private TableColumn<Expenses, String> descColumn;
    @FXML
    private TableColumn<Expenses, String> dueDateColumn;


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Room room;

    private RoomService expenseService;
    private String username;

    public void setService(RoomService roomService, String username) {
        this.expenseService = roomService;
        this.username = username;
        loadExpenses();
    }

    @FXML
    public void initialize() {
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getExpenseType()));
        amountColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getAmount()));
        descColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        dueDateColumn.setCellValueFactory(cellData -> {
            LocalDateTime dueDate = cellData.getValue().getDueDate();
            String formattedDate = (dueDate != null) ? dueDate.toLocalDate().format(formatter) : "";
            return new javafx.beans.property.SimpleStringProperty(formattedDate);

        });
    }



    @FXML
    public void onSaveButtonClick() {
        try {
            String type = typeField.getText();
            Float amount = Float.parseFloat(amountField.getText());
            String desc = descriptionField.getText();
            LocalDate date = dueDatePicker.getValue();

            if (type.isEmpty() || desc.isEmpty() || date == null) {
                throw new IllegalArgumentException("All fields must be filled!");
            }

            Expenses expense = new Expenses(username, type, amount, desc, date.atStartOfDay(),room.getCode() );
            expenseService.addExpense(expense);

            loadExpenses();
            typeField.clear();
            amountField.clear();
            descriptionField.clear();
            dueDatePicker.setValue(null);

            //((Stage) typeField.getScene().getWindow()).close();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }


    private void loadExpenses() {
        if (expenseService != null && room != null) {
            List<Expenses> expensesList = expenseService.getExpensesForRoom(room.getCode());
            ObservableList<Expenses> obsList = FXCollections.observableArrayList(expensesList);
            expensesTable.setItems(obsList);
        }
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

