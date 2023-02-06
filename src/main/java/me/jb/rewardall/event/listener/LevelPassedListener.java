package me.jb.rewardall.event.listener;

import com.sun.istack.internal.NotNull;
import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.discord.DiscordManager;
import me.jb.rewardall.enums.config.Message;
import me.jb.rewardall.event.LevelPassedEvent;
import me.jb.rewardall.event.MaxProgressEvent;
import me.jb.rewardall.points.data.PointsModel;
import me.jb.rewardall.points.data.dataobject.MessageContent;
import me.jb.rewardall.utils.MessageUtils;
import me.jb.rewardall.RewardAll;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LevelPassedListener implements Listener {

    private final PointsModel pointsModel;
    private final FileHandler fileHandler;

    public LevelPassedListener(@NotNull RewardAll rewardAll) {
        this.pointsModel = rewardAll.getPointsModel();
        this.fileHandler = rewardAll.getFileHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLevelPassed(LevelPassedEvent event) {

        if (event.isCancelled())
            return;

        DiscordManager discordManager = pointsModel.getDiscordManager();
        MessageContent messageContent = event.getLevel().getMessageContent();

        discordManager.sendEmbed(messageContent);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMaxProgress(MaxProgressEvent event) {

        if (event.isCancelled())
            return;

        FileConfiguration messageConfig = this.fileHandler.getMessageConfig().getConfig();
        String message = MessageUtils.setColorsMessage(messageConfig.getString(Message.COMPLETE.getKey()));

        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(message));

    }
}
