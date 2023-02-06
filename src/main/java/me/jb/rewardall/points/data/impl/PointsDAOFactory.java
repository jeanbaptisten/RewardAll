package me.jb.rewardall.points.data.impl;

import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.database.DbConnection;
import me.jb.rewardall.points.data.PointsDAO;
import me.jb.rewardall.database.impl.MySQLDbConnection;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class PointsDAOFactory {

    public static PointsDAO getPointsDAO(@NotNull FileHandler fileHandler) {
        ConfigurationSection configurationSection = fileHandler.getMainConfigFile().getConfig().getConfigurationSection("storage");
        String type = configurationSection.getString("storage-method");

        switch (type) {
            case "YAML":
                return new YAMLPointsDAO(fileHandler);

            case "MySQL":
                String host = configurationSection.getString("mysql.host");
                String database = configurationSection.getString("mysql.database");
                String user = configurationSection.getString("mysql.username");
                String password = configurationSection.getString("mysql.password");

                DbConnection dbConnection = new MySQLDbConnection(host, database, user, password);

                try {
                    dbConnection.open();
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                return new SQLPointsDAO(dbConnection);

            default:
                throw new IllegalStateException("Db type " + type + " isn't supported or doesn't exist.");
        }
    }

}
