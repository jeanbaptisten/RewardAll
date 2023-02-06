package me.jb.rewardall.points.data;

public interface PointsService {

    void loadData();

    int getProgress();

    void addPoints(int points);

    void setProgress(int newProgress);

    void reload();

    void savePoints();

    void resetPoints();

}
