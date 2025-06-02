package src.roommate.roommatebasicapp.repository.DbRepo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.roommate.roommatebasicapp.domain.RoomsMembers;
import src.roommate.roommatebasicapp.domain.User;
import src.roommate.roommatebasicapp.repository.Interfaces.IUserDbRepo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDbRepo implements IUserDbRepo {

    JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();


    public UserDbRepo(Properties props) {
        logger.info("Initializing UserDBRepo with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"users\"")) {
            ResultSet resultSet = ps.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String nickname = resultSet.getString("nickname");
                User user = new User(username, password, nickname);
//                Log.d("tag", "restul");
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
//            throw new RuntimeException(e);
//            Log.E("ErRROR","ERR");
            return null;
        }
    }

    @Override
    public User getOne(String string) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"users\" WHERE username=?")) {

            ps.setString(1, string);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String nickname = resultSet.getString("nickname");
                return new User(username, password, nickname);
            } else {
                System.out.println("No user found with username: " + string);
                return null;
            }

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return null;
        }
    }


    @Override
    public boolean saveEntity(User entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO \"users\" (\"username\",\"password\", \"nickname\") VALUES(?,?,?)")) {
            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getNickname());
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
             PreparedStatement ps = connection.prepareStatement("DELETE FROM \"users\" WHERE \"username\" = ?")) {
            ps.setString(1, string);

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
    public boolean updateEntity(User entity) {
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE \"users\" SET \"username\" = ?, \"password\" = ?, \"nickname\" = ? WHERE \"username\" = ?")){

            ps.setString(1, entity.getUsername());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getNickname());
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
    public boolean saveEntityRM(RoomsMembers roomsMembers) {
        return false;
    }

    @Override
    public void addScore(String username, int score) {
        String sql = "INSERT INTO score_points (username, score) VALUES (?, ?)";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setInt(2, score);

            ps.executeUpdate();
            logger.info("Score added successfully for user: {}", username);

        } catch (SQLException e) {
            logger.error("Error adding score for user {}: {}", username, e.getMessage());
            throw new RuntimeException("Failed to add score for user " + username, e);
        }
    }
    @Override
    public int getUserScore(String username) {
        String sql = "SELECT SUM(score) AS total_score FROM score_points WHERE username = ?";

        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                int totalScore = resultSet.getInt("total_score");
                logger.info("Total score for user {}: {}", username, totalScore);
                return totalScore;
            } else {
                logger.warn("No scores found for user: {}", username);
                return 0;
            }

        } catch (SQLException e) {
            logger.error("Error retrieving score for user {}: {}", username, e.getMessage());
            throw new RuntimeException("Failed to retrieve score for user " + username, e);
        }
    }

}
