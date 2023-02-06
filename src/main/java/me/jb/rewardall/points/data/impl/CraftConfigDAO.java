package me.jb.rewardall.points.data.impl;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.points.data.ConfigDAO;
import me.jb.rewardall.points.data.dataobject.Level;
import me.jb.rewardall.points.data.dataobject.MessageContent;
import me.jb.rewardall.points.data.dataobject.points.ConstantPointsData;
import me.jb.rewardall.points.reward.impl.CommandReward;
import me.jb.rewardall.RewardAll;
import me.jb.rewardall.points.data.dataobject.points.PointsData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class CraftConfigDAO implements ConfigDAO {

    private final FileHandler fileHandler;
    private final Logger logger;

    public CraftConfigDAO(@NotNull RewardAll rewardAll) {
        this.fileHandler = rewardAll.getFileHandler();
        this.logger = rewardAll.getLogger();
    }

    @Override
    public PointsData loadData() {
        FileConfiguration mainFile = this.fileHandler.getMainConfigFile().getConfig();

        // Loading all levels
        List<Level> levelList = new ArrayList<>();
        ConfigurationSection levelSection = mainFile.getConfigurationSection("levels");
        assert levelSection != null;
        Set<String> levelsKey = levelSection.getKeys(false);

        // Looping on all keys (= int level) then sorting them
        levelsKey.forEach(level -> {
            ConfigurationSection actualLevelSection = levelSection.getConfigurationSection(level);
            assert actualLevelSection != null;

            ConfigurationSection actualLevelContentSection = actualLevelSection.getConfigurationSection(".embedMessage");
            assert actualLevelContentSection != null;

            boolean hasSpecialMention = actualLevelSection.getBoolean("specialMention");
            EmbedBuilder newEmbedBuilder = this.buildEmbed(actualLevelContentSection, level);

            MessageContent messageContent = new MessageContent(newEmbedBuilder, hasSpecialMention);

            Level newLevel = new Level(Integer.parseInt(level), messageContent);
            levelList.add(newLevel);
        });
        this.sortList(levelList);

        // Getting max count and current progress
        int maxCount = mainFile.getInt("maxCount");
        FileConfiguration dataFile = this.fileHandler.getDataFile().getConfig();

        // Getting reward command
        String commandRewardString = mainFile.getString("maxPointCommand");
        CommandReward commandReward = new CommandReward(commandRewardString);

        // Creating ConstantPointsData object
        ConstantPointsData constantPointsData = new ConstantPointsData(levelList, maxCount, commandReward);

        // Creating PointsData object
        return new PointsData(constantPointsData);
    }
    @Override
    public String loadTextChannel() {
        FileConfiguration configFile = this.fileHandler.getMainConfigFile().getConfig();

        return configFile.getString("channelId");
    }

    @Override
    public String loadSpecialMentionText() {
        FileConfiguration configFile = this.fileHandler.getMainConfigFile().getConfig();

        return configFile.getString("specialMentionText");
    }

    private EmbedBuilder buildEmbed(ConfigurationSection actualLevelSection, String level) {

        // Retrieving data related to the embed message
        String[] color = actualLevelSection.getString(".color").split(",");
        String footer = actualLevelSection.getString("footer");
        String imageFooter = actualLevelSection.getString("imageFooter");
        String title = actualLevelSection.getString("title");
        String thumbnail = actualLevelSection.getString("thumbnail");
        boolean enableTimestamp = actualLevelSection.getBoolean("enableTimestamp");

        // Creating embed message
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2])));
        embedBuilder.setFooter(footer, imageFooter);
        if (!title.equals("none")) embedBuilder.setTitle(title);
        if (!thumbnail.equals("none")) embedBuilder.setThumbnail(thumbnail);
        if (enableTimestamp) embedBuilder.setTimestamp(Instant.now());

        // Adding content
        this.addContent(actualLevelSection, embedBuilder);

        return embedBuilder;
    }

    private void addContent(ConfigurationSection actualLevelSection, EmbedBuilder embedBuilder) {
        ConfigurationSection contentSection = actualLevelSection.getConfigurationSection("content");
        Set<String> contentKeys = contentSection.getKeys(false);

        for (String contentKey : contentKeys) {
            ConfigurationSection customContentSection = contentSection.getConfigurationSection(contentKey);
            assert customContentSection != null;

            String contentType = customContentSection.getString("type");
            assert contentType != null;

            switch (contentType) {
                case "TEXT":
                    String name = customContentSection.getString("name");
                    String value = customContentSection.getString("value");
                    boolean inline = customContentSection.getBoolean("inline");

                    embedBuilder.addField(name, value, inline);
                    break;
                case "BLANK":
                    boolean inlineContent = customContentSection.getBoolean("inline");

                    embedBuilder.addBlankField(inlineContent);
                    break;
                default:
                    this.logger.log(java.util.logging.Level.INFO, "[RewardAll] Type \"" + contentType + "\" does'nt exist.");
                    this.logger.log(java.util.logging.Level.INFO, "[RewardAll] Content \"" + customContentSection.getCurrentPath() + "\" will be ignored.");
                    break;
            }
        }
    }

    private void sortList(List<Level> levels) {
        levels.sort(Comparator.comparingInt(Level::getLevel));
    }
}
