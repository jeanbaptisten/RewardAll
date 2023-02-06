package me.jb.rewardall.points.data.dataobject.points;

import com.sun.istack.internal.NotNull;
import me.jb.rewardall.points.data.dataobject.Level;
import me.jb.rewardall.points.reward.Reward;

import java.util.Collections;
import java.util.List;

public class ConstantPointsData {

    private final List<Level> levelList;
    private final int maxCount;
    private final Reward reward;

    public ConstantPointsData(@NotNull List<Level> levelList, int maxCount, Reward reward) {
        this.levelList = levelList;
        this.maxCount = maxCount;
        this.reward = reward;
    }

    public Reward getReward(){
        return this.reward;
    }

    public List<Level> getLevelList(){
        return Collections.unmodifiableList(this.levelList);
    }

    public int getMaxCount(){
        return this.maxCount;
    }

}
