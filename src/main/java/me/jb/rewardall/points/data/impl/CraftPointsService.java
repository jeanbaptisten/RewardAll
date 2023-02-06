package me.jb.rewardall.points.data.impl;

import me.jb.rewardall.discord.DiscordManager;
import me.jb.rewardall.event.LevelPassedEvent;
import me.jb.rewardall.event.MaxProgressEvent;
import me.jb.rewardall.points.data.ConfigDAO;
import me.jb.rewardall.points.data.PointsDAO;
import me.jb.rewardall.points.data.PointsModel;
import me.jb.rewardall.points.data.PointsService;
import me.jb.rewardall.points.data.dataobject.points.PointsData;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CraftPointsService implements PointsService {

    private final ConfigDAO configDAO;
    private final PointsDAO pointsDao;
    private final PointsModel pointsModel;

    public CraftPointsService(@NotNull ConfigDAO configDAO, @NotNull PointsDAO pointsDao, @NotNull PointsModel pointsModel) {
        this.configDAO = configDAO;
        this.pointsDao = pointsDao;
        this.pointsModel = pointsModel;
    }

    @Override
    public void loadData() {
        PointsData pointsData = this.configDAO.loadData();
        int currentProgress = -1;
        try {
            currentProgress = this.pointsDao.loadCurrentProgress();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String textChannelId = this.configDAO.loadTextChannel();
        String specialMentionText = this.configDAO.loadSpecialMentionText();

        pointsData.setActualProgress(currentProgress);
        this.pointsModel.setData(pointsData);
        this.pointsModel.setDiscordManager(new DiscordManager(textChannelId, specialMentionText));
    }

    @Override
    public int getProgress() {
        try {
            return this.pointsDao.loadCurrentProgress();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    @Override
    public void addPoints(int points) {
        if (points < 0)
            throw new IllegalArgumentException("Added points cannot be negative.");

        PointsData pointsData = this.pointsModel.getData();

        int actualProgress = this.getProgress();
        pointsData.setActualProgress(actualProgress);
        this.pointsModel.setData(pointsData);

        int maxProgress = pointsData.getMaxProgress();
        int newProgress = actualProgress + points;

        // If not the last level
        if (!pointsData.hasReachedLastLevel()) {

            // If level passed, get next level
            if (pointsData.getActualLevel().getLevel() <= newProgress && maxProgress > newProgress) {

                // Setting up level
                LevelPassedEvent levelPassedEvent = new LevelPassedEvent(pointsData.getActualLevel());
                Bukkit.getPluginManager().callEvent(levelPassedEvent);

                if (!pointsData.nextLevel())
                    return;
            }
        }

        // If max progress reached
        if (maxProgress <= newProgress) {

            MaxProgressEvent maxProgressEvent = new MaxProgressEvent(maxProgress);
            Bukkit.getPluginManager().callEvent(maxProgressEvent);

            // Reset, give reward then set new progress.
            this.resetPoints();
            pointsData.getConstantData().getReward().give();
            int nextProgress = (actualProgress + points) % maxProgress;
            this.addPoints(nextProgress);

            this.savePoints();
            return;
        }

        // Else
        this.pointsModel.addPoints(points);
        this.savePoints();
    }

    @Override
    public void setProgress(int newProgress) {
        this.pointsModel.getData().setActualProgress(newProgress);
        this.savePoints();
    }

    @Override
    public void reload() {
        this.savePoints();
        this.loadData();
    }

    @Override
    public void savePoints() {
        int currentProgress = this.pointsModel.getProgress();

        this.pointsDao.savePoints(currentProgress);
    }

    @Override
    public void resetPoints() {
        this.pointsModel.resetProgress();
        this.savePoints();
    }
}
