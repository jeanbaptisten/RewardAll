package me.jb.rewardall.points.data.impl;

import me.jb.rewardall.database.DbConnection;
import me.jb.rewardall.points.data.PointsDAO;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLPointsDAO implements PointsDAO {

    private final DbConnection dbConnection;

    public SQLPointsDAO(@NotNull DbConnection dbConnection) {
        this.dbConnection = dbConnection;

        this.initTable();
    }

    @Override
    public int loadCurrentProgress() throws SQLException {
        String sqlQuery = "SELECT * FROM `dragon_data`;";
        Connection connection = dbConnection.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getInt("data_value");
            else return -1;
        }
    }

    @Override
    public void savePoints(int points) {
        String stringSQL = "UPDATE `dragon_data` SET `data_value`=? WHERE `data_id`=1;";
        Connection connection = dbConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(stringSQL)) {

            statement.setInt(1, points);

            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }

    private void initTable() {
        String sqlQueryCreate = "CREATE TABLE IF NOT EXISTS `dragon_data` (" +
                "    `data_id` INT NOT NULL AUTO_INCREMENT," +
                "    `data_value` INT NOT NULL," +
                "    PRIMARY KEY (`data_id`)" +
                ");" +
                "INSERT INTO `dragon_data` (`data_id`, `data_value`)" +
                "SELECT 1,0 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `dragon_data`);";

        Connection connection = this.dbConnection.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(sqlQueryCreate)) {
            statement.execute();
        } catch (SQLException troubles) {
            troubles.printStackTrace();
        }
    }
}
