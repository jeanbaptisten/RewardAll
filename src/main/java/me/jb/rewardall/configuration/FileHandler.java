package me.jb.rewardall.configuration;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileHandler {

    private CustomConfig mainConfig;
    private CustomConfig dataConfig;
    private CustomConfig messageConfig;

    private final Plugin plugin;

    public FileHandler(@NotNull Plugin plugin) {
        this.plugin = plugin;

        // Initialize main configuration file.
        this.setupMainConfigFile();

        // Initialize data file.
        this.setupDataFile();

        // Initialize message configuration file.
        this.setupMessageConfigFile();

    }

    @NotNull
    public CustomConfig getMainConfigFile() {
        return this.mainConfig;
    }

    @NotNull
    public CustomConfig getDataFile() {
        return this.dataConfig;
    }

    @NotNull
    public CustomConfig getMessageConfig() {
        return this.messageConfig;
    }

    public void reloadFiles() {
        this.setupMainConfigFile();
        this.setupDataFile();
        this.setupMessageConfigFile();
    }

    private void setupMainConfigFile() {
        File mainConfigFile = FileEngine.fileCreator(this.plugin, "config.yml");
        this.mainConfig = new CustomConfig(mainConfigFile);
    }

    private void setupMessageConfigFile() {
        File messageConfigFile = FileEngine.fileCreator(this.plugin, "messages.yml");
        this.messageConfig = new CustomConfig(messageConfigFile);
    }

    private void setupDataFile() {
        File dataFile = FileEngine.fileCreator(this.plugin, "data.yml");
        this.dataConfig = new CustomConfig(dataFile);
    }


}