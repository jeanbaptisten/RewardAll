package me.jb.rewardall.points.reward.impl;

import me.jb.rewardall.points.reward.Reward;
import org.bukkit.Bukkit;

public class CommandReward implements Reward {

    private final String command;

    public CommandReward(String command){
        this.command = command;
    }

    @Override
    public void give() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.command);
    }
}
