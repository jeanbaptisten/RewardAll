package me.jb.rewardall.command;

import me.jb.rewardall.points.data.PointsModel;
import me.jb.rewardall.RewardAll;
import me.jb.rewardall.configuration.FileHandler;
import me.jb.rewardall.enums.Permission;
import me.jb.rewardall.enums.config.Message;
import me.jb.rewardall.points.data.PointsService;
import me.jb.rewardall.points.data.dataobject.points.PointsData;
import me.jb.rewardall.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class CmdRewardall implements CommandExecutor {

    private final RewardAll rewardAll;
    public final PointsService pointsService;
    public final PointsModel pointsModel;
    public final FileHandler fileHandler;

    public CmdRewardall(@NotNull RewardAll rewardAll) {
        this.rewardAll = rewardAll;
        this.pointsService = rewardAll.getPointsService();
        this.pointsModel = rewardAll.getPointsModel();
        this.fileHandler = rewardAll.getFileHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        FileConfiguration messageConfig = this.fileHandler.getMessageConfig().getConfig();

        // Check perm
        if (!sender.hasPermission(Permission.USE.getPermission())) {
            String noPermMess = messageConfig.getString(Message.NO_PERM.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(noPermMess));
            return true;
        }

        // Check args → /votedragon <points>/reload/reset
        if (args.length != 1 && args.length != 2) {
            String errorMess = messageConfig.getString(Message.ERROR.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(errorMess));
            return true;
        }

        switch (args[0]) {
            case "reload":
                this.reloadCmd(sender, messageConfig);
                break;

            case "reset":
                this.resetCmd(sender, messageConfig);
                break;

            case "set":
                this.setCmd(sender, messageConfig, args[1]);
                break;

            default:
                this.addPointsCmd(sender, messageConfig, args[0]);
                break;
        }

        return true;
    }

    private void setCmd(CommandSender sender, FileConfiguration messageConfig, String arg) {
        if (!sender.hasPermission(Permission.SET.getPermission())) {
            String noPerMess = messageConfig.getString(Message.NO_PERM.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(noPerMess));
        }

        // Check if argument is an integer
        if (this.isntStringInt(arg)) {
            String notIntSetMess = messageConfig.getString(Message.NOT_INT_SET.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(notIntSetMess));
            return;
        }

        PointsData pointsData = this.pointsModel.getData();
        int points = Integer.parseInt(arg);

        if (points >= pointsData.getMaxProgress()) {
            String tooHighMess = messageConfig.getString(Message.TOO_HIGH.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(tooHighMess));
            return;
        }

        this.pointsService.setProgress(points);

        String setMess = messageConfig.getString(Message.SET.getKey()).replace("%points%", arg);
        sender.sendMessage(MessageUtils.setColorsMessage(setMess));
    }

    private void resetCmd(CommandSender sender, FileConfiguration messageConfig) {
        if (!sender.hasPermission(Permission.RESET.getPermission())) {
            String noPerMess = messageConfig.getString(Message.NO_PERM.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(noPerMess));
        }

        this.pointsService.resetPoints();

        String resetMess = messageConfig.getString(Message.RESET_POINTS.getKey());
        sender.sendMessage(MessageUtils.setColorsMessage(resetMess));
    }

    private void reloadCmd(CommandSender sender, FileConfiguration messageConfig) {
        if (!sender.hasPermission(Permission.RELOAD.getPermission())) {
            String noPerMess = messageConfig.getString(Message.NO_PERM.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(noPerMess));
        }

        this.rewardAll.reloadPlugin();

        String reloadMess = messageConfig.getString(Message.RELOAD.getKey());
        sender.sendMessage(MessageUtils.setColorsMessage(reloadMess));

    }

    private void addPointsCmd(CommandSender sender, FileConfiguration messageConfig, String arg) {
        // Check if argument is an integer
        if (this.isntStringInt(arg)) {
            String notIntMess = messageConfig.getString(Message.NOT_INT.getKey());
            sender.sendMessage(MessageUtils.setColorsMessage(notIntMess));
            return;
        }

        this.pointsService.addPoints(Integer.parseInt(arg));
        String notIntMess = messageConfig.getString(Message.ADD_POINTS.getKey()).replace("%points%", arg);
        sender.sendMessage(MessageUtils.setColorsMessage(notIntMess));
    }

    private boolean isntStringInt(String s) {
        try {
            Integer.parseInt(s);
            return false;
        } catch (NumberFormatException ex) {
            return true;
        }
    }
}
