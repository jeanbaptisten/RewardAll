package me.jb.rewardall.points.data.impl;

import me.jb.rewardall.configuration.CustomConfig;
import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.points.data.PointsDAO;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class YAMLPointsDAO implements PointsDAO {
    private final FileHandler fileHandler;

    public YAMLPointsDAO(@NotNull FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    @Override
    public int loadCurrentProgress() {
        FileConfiguration dataFile = this.fileHandler.getDataFile().getConfig();

        return dataFile.getInt("currentProgress");
    }

    @Override
    public void savePoints(int points) {
        CustomConfig dataFile = this.fileHandler.getDataFile();

        dataFile.getConfig().set("currentProgress", points);
        dataFile.save();
    }

}
