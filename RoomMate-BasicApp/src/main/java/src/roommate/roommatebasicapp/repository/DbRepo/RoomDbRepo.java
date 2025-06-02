package src.roommate.roommatebasicapp.repository.DbRepo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.Room;
import src.roommate.roommatebasicapp.domain.RoomsMembers;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.repository.Interfaces.IRoomDbRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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




}
