package src.roommate.roommatebasicapp.repository.DbRepo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.*;
import src.roommate.roommatebasicapp.repository.Interfaces.IRoomDbRepo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RoomDbRepo implements IRoomDbRepo {

    JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public RoomDbRepo(Properties props) {
        logger.info("Initializing RoomDBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Room> getAll() {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"rooms\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                String code = resultSet.getString("code");
                int memberNr = resultSet.getInt("memberNr");
                String name = resultSet.getString("name");
                Room room = new Room(code, memberNr, name);
//                Log.d("tag", "restul");
                rooms.add(room);
            }
            return rooms;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public Room getOne(String roomCode) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM rooms WHERE code = ?")) {

            ps.setString(1, roomCode);

            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String code = resultSet.getString("code");
                int memberNr = resultSet.getInt("memberNr");
                String name = resultSet.getString("name");

                return new Room(code, memberNr, name);
            } else {
                System.out.println("Room not found for code: " + roomCode);
                return null;
            }

        } catch (SQLException e) {
            System.out.println("SQL error in getOne(Room): " + e.getMessage());
            return null;
        }
    }



    @Override
    public boolean saveEntity(Room entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"rooms\" (\"code\",\"memberNr\", \"name\") VALUES(?,?,?)")) {
            ps.setString(1, entity.getCode());
            ps.setInt(2, entity.getMemberNr());
            ps.setString(3, entity.getName());
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //           Log.E("ErRROR","ERR");
            //          return false;
        }
    }



    @Override
    public boolean deleteEntity(String string) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"rooms\" WHERE \"code\" = ?")) {
            //ps.setInt(1, id);
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    @Override
    public boolean updateEntity(Room entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"rooms\" SET \"code\" = ?,  \"memberNr\" = ? ,\"name\" WHERE \"code\" = ?")) {
            ps.setString(1, entity.getCode());
            ps.setInt(2, entity.getMemberNr());
            ps.setString(3, entity.getName());
            ps.executeUpdate();
//                Log.d("tag", "restul");
            return true;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return false;
        }
    }

    // -----------------Rooms-Members




    public boolean saveEntityRM(RoomsMembers rm) {
        String sql = "INSERT INTO rooms_members (room_code, member_username, admin) VALUES (?, ?, ?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, rm.getRoom_code());
            ps.setString(2, rm.getMember_username());
            ps.setBoolean(3, rm.isAdmin());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("SQL error in saveEntityRM: " + e.getMessage());
            throw new RuntimeException(e);

        }
    }


    @Override
    public Iterable<RoomsMembers> findByUsername(String username) {
        List<RoomsMembers> result = new ArrayList<>();

        String sql = "SELECT * FROM rooms_members WHERE member_username = ?";
        try (Connection conn = dbUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String roomCode = rs.getString("room_code");
                boolean isAdmin = rs.getBoolean("admin");
                result.add(new RoomsMembers(roomCode, username, isAdmin));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving rooms for user: " + username, e);
        }

        return result;
    }

    @Override
    public boolean saveExpense(Expenses expense) {
        String sql = "INSERT INTO expenses (username, expenseType, amount, description, dueDate,roomCode) VALUES (?, ?, ?, ?, ?,?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, expense.getUsername());
            ps.setString(2, expense.getExpenseType());
            ps.setFloat(3, expense.getAmount());
            ps.setString(4, expense.getDescription());
            ps.setString(5, expense.getDueDate().toString());
            ps.setString(6, expense.getRoomCode());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("SQL error in saveExpense: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Expenses> getExpenses(String roomCode) {
        List<Expenses> expensesList = new ArrayList<>();
        String sql = "SELECT username, expenseType, amount, description, dueDate, roomCode FROM expenses WHERE roomCode = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, roomCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String username = rs.getString("username");
                    String expenseType = rs.getString("expenseType");
                    Float amount = rs.getFloat("amount");
                    String description = rs.getString("description");
                    //Timestamp timestamp = rs.getTimestamp("dueDate");
                    LocalDateTime dueDate = null;
                    try {
                        String raw = rs.getString("dueDate");
                        if (raw != null && !raw.isEmpty()) {
                            // Încearcă să parsezi cu ISO_LOCAL_DATE_TIME
                            dueDate = LocalDateTime.parse(raw);
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to parse dueDate for record: " + rs.getString("description") + ", value: " + rs.getString("dueDate"));
                        throw e;
                    }

                    // room_code is known (roomCode), can be passed if your Expenses constructor needs it

                    Expenses expense = new Expenses(username, expenseType, amount, description, dueDate, roomCode);
                    expensesList.add(expense);
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error in getExpenses: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return expensesList;
    }

    @Override
    public List<Chores> getChores(String roomCode) {
        List<Chores> choresList = new ArrayList<>();
        String sql = "SELECT choreName, status, roomCode FROM chores WHERE roomCode = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, roomCode);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String choreName = rs.getString("choreName");
                    String status = rs.getString("status");
                    String room = rs.getString("roomCode");
                    ChoreStatus choreStatus = ChoreStatus.valueOf(status);

                    choresList.add(new Chores(choreName, choreStatus, room));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL error in getChores: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return choresList;
    }


    @Override
    public void addChore(String roomCode, Chores chore) {
        String sql = "INSERT INTO chores (choreName, status, roomCode) VALUES (?, ?, ?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, chore.getChoreName());
            ps.setString(2, chore.getStatus().toString());
            ps.setString(3, roomCode);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("SQL error in addChore: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public void updateChore(Chores chore) {
        String sql = "UPDATE chores SET status = ? WHERE choreName = ? AND roomCode = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, chore.getStatus().toString());
            ps.setString(2, chore.getChoreName());
            ps.setString(3, chore.getRoomCode());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("No chore found to update: " + chore.getChoreName());
            }
        } catch (SQLException e) {
            System.err.println("SQL error in updateChore: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


   @Override
    public List<User> findUsersByRoom(String roomCode) {
        String sql = "SELECT u.username, u.password, u.nickname FROM users u " +
                "JOIN rooms_members rm ON u.username = rm.member_username " +
                "WHERE rm.room_code = ?";

        List<User> users = new ArrayList<>();
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, roomCode);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");

                users.add(new User(username, password, nickname));
            }
        } catch (SQLException e) {
            logger.error("Error loading users for room {}: {}", roomCode, e.getMessage());
        }
        return users;
    }




}
