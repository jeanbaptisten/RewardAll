package me.jb.rewardall.points.data.impl;

import me.jb.rewardall.discord.DiscordManager;
import me.jb.rewardall.points.data.PointsModel;
import me.jb.rewardall.points.data.dataobject.points.PointsData;
import org.jetbrains.annotations.NotNull;

public class CraftPointsModel implements PointsModel {

    private PointsData pointsData;
    private DiscordManager discordManager;

    @Override
    public PointsData getData() {
        return this.pointsData;
    }

    @Override
    public void setData(@NotNull PointsData constantPointsData) {
        this.pointsData = constantPointsData;
    }

    @Override
    public void addPoints(int pointsToAdd) {
        if (this.pointsData.getMaxProgress() < this.pointsData.getProgress() + pointsToAdd)
            throw new IllegalStateException("You can add so much points !");

        this.pointsData.addPoints(pointsToAdd);
    }

    @Override
    public void resetProgress() {
        this.pointsData.resetProgress();
    }

    @Override
    public int getProgress() {
        return this.pointsData.getProgress();
    }

    @Override
    public void setDiscordManager(@NotNull DiscordManager discordManager) {
        this.discordManager = discordManager;
    }

    @Override
    public DiscordManager getDiscordManager() {
        return this.discordManager;
    }
}
