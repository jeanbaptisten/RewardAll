package me.jb.rewardall;

import me.jb.rewardall.command.CmdRewardall;
import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.event.listener.LevelPassedListener;
import me.jb.rewardall.points.data.ConfigDAO;
import me.jb.rewardall.points.data.PointsDAO;
import me.jb.rewardall.points.data.PointsModel;
import me.jb.rewardall.points.data.impl.CraftConfigDAO;
import me.jb.rewardall.points.data.impl.CraftPointsModel;
import me.jb.rewardall.points.data.impl.CraftPointsService;
import me.jb.rewardall.points.data.impl.PointsDAOFactory;
import me.jb.rewardall.placeholder.RewardAllExpansion;
import me.jb.rewardall.points.data.PointsService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class RewardAll extends JavaPlugin {

    private final FileHandler fileHandler = new FileHandler(this);
    private PointsModel pointsModel;
    private PointsService pointsService;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Setting points service
        this.setupPointsService();

        // Register listeners
        this.registerListeners();

        // Register commands
        this.registerCommands();

        // Register placeholders
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.registerPlaceholders();
        }

        // Plugin enabled
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.pointsService.savePoints();
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new LevelPassedListener(this), this);
    }

    private void registerCommands() {
        super.getCommand("rewardall").setExecutor(new CmdRewardall(this));
    }

    private void setupPointsService() {
        ConfigDAO configDAO = new CraftConfigDAO(this);

        PointsDAO pointsDao = PointsDAOFactory.getPointsDAO(this.fileHandler);

        this.pointsModel = new CraftPointsModel();
        this.pointsService = new CraftPointsService(configDAO, pointsDao, this.pointsModel);

        this.pointsService.loadData();
    }

    private void registerPlaceholders() {
        new RewardAllExpansion(this).register();
    }

    @NotNull
    public PointsService getPointsService() {
        return this.pointsService;
    }

    @NotNull
    public PointsModel getPointsModel() {
        return this.pointsModel;
    }

    @NotNull
    public FileHandler getFileHandler() {
        return this.fileHandler;
    }

    public void reloadPlugin() {
        this.fileHandler.reloadFiles();
        this.pointsService.reload();
    }
}
