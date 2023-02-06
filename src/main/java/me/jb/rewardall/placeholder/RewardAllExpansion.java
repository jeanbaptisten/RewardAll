package me.jb.rewardall.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.jb.rewardall.RewardAll;
import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.points.data.PointsModel;
import me.jb.rewardall.points.data.PointsService;
import me.jb.rewardall.utils.MessageUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class RewardAllExpansion extends PlaceholderExpansion {

    private final FileHandler fileHandler;
    private final PointsModel pointsModel;
    private final PointsService pointsService;

    public RewardAllExpansion(@NotNull RewardAll rewardAll) {
        this.fileHandler = rewardAll.getFileHandler();
        this.pointsService = rewardAll.getPointsService();
        this.pointsModel = rewardAll.getPointsModel();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "rewardall";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Jb";
    }

    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {

        if (!params.equals("count")) {
            return null;
        }

        FileConfiguration mainConfig = this.fileHandler.getMessageConfig().getConfig();

        int currentProgress = pointsService.getProgress();
        int maxProgress = pointsModel.getData().getMaxProgress();

        String format = mainConfig.getString("placeholderFormat")
                .replace("%actual%", String.valueOf(currentProgress))
                .replace("%max%", String.valueOf(maxProgress));

        return MessageUtils.setColorsMessage(format);
    }
}
