package me.jb.rewardall.points.data;

import me.jb.rewardall.discord.DiscordManager;
import me.jb.rewardall.points.data.dataobject.points.PointsData;

public interface PointsModel {

    PointsData getData();

    void setData(PointsData constantPointsData);

    void addPoints(int pointsToAdd);

    void resetProgress();

    int getProgress();

    void setDiscordManager(DiscordManager discordManager);

    DiscordManager getDiscordManager();

}
