package me.jb.rewardall.points.data.dataobject.points;

import me.jb.rewardall.points.data.dataobject.Level;

import java.util.List;

public class PointsData {

    private final ConstantPointsData pointsData;
    private int actualProgress;
    private Level actualLevel;

    public PointsData(ConstantPointsData constantPointsData, int actualProgress) {
        this.pointsData = constantPointsData;
        this.actualProgress = actualProgress;
        this.setActualLevel();
    }

    public PointsData(ConstantPointsData constantPointsData) {
        this(constantPointsData, 0);
    }

    public Level getActualLevel() {
        return this.actualLevel;
    }

    public ConstantPointsData getConstantData() {
        return this.pointsData;
    }

    public boolean nextLevel() {
        List<Level> levels = this.pointsData.getLevelList();
        int actualLevelIndex = levels.indexOf(this.actualLevel);

        // If actualLevel = null → If last level passed
        if (this.actualLevel == null)
            return false;

        if (this.getMaxProgress() == actualProgress)
            return false;

        if (this.isLastLevel()) {
            this.actualLevel = null;
            return true ;
        }

        this.actualLevel = levels.get(actualLevelIndex + 1);

        return true;
    }


    public void resetProgress() {
        this.actualProgress = 0;

        List<Level> levels = this.pointsData.getLevelList();
        this.actualLevel = levels.get(0);
    }

    public void setActualProgress(int points) {
        if (points >= this.pointsData.getMaxCount())
            throw new IllegalArgumentException("Actual progress cannot be higher or equal than max progress.");

        this.actualProgress = points;
        this.setActualLevel();
    }

    public boolean hasReachedLastLevel() {
        return this.actualLevel == null;
    }

    public int getMaxProgress() {
        return this.pointsData.getMaxCount();
    }

    public int getProgress() {
        return this.actualProgress;
    }

    public void addPoints(int pointsToAdd) {
        this.actualProgress += pointsToAdd;
    }

    private Level getLastLevel() {
        List<Level> levels = this.pointsData.getLevelList();
        return levels.get(levels.size() - 1);
    }

    private boolean isLastLevel() {
        List<Level> levels = this.pointsData.getLevelList();
        return levels.indexOf(this.actualLevel) + 1 >= levels.size();
    }

    private void setActualLevel() {
        List<Level> levels = this.pointsData.getLevelList();
        Level selectedLevel = levels.get(0);
        int selectedLevelIndex = 0;
        while (this.actualProgress > selectedLevel.getLevel() &&
                selectedLevel.getLevel() < this.getLastLevel().getLevel()) {
            selectedLevel = levels.get(selectedLevelIndex + 1);
            selectedLevelIndex++;
        }

        if (this.actualProgress >= this.getLastLevel().getLevel())
            selectedLevel = null;

        this.actualLevel = selectedLevel;
    }

}
