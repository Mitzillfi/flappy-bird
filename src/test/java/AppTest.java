import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AppTest {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/highscore",
                "reguser", "2504321")) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO \"leaderboard\" (name, score)" + "Values('hello','50')");
            statement.executeUpdate("INSERT INTO \"leaderboard\" (name, score)" + "Values('hi','10')");
            statement.executeUpdate("INSERT INTO \"leaderboard\" (name, score)" + "Values('hehehe','11')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
