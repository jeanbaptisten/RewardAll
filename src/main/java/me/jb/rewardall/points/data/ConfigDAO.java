package me.jb.rewardall.points.data;

import me.jb.rewardall.points.data.dataobject.points.PointsData;

public interface ConfigDAO {

    PointsData loadData();

    String loadTextChannel();

    String loadSpecialMentionText();
}
