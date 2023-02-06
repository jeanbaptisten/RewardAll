package me.jb.rewardall.points.data;

import java.sql.SQLException;

public interface PointsDAO {

    int loadCurrentProgress() throws SQLException;

    void savePoints(int points);
}
