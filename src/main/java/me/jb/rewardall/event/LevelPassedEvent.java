package me.jb.rewardall.event;

import me.jb.rewardall.points.data.dataobject.Level;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LevelPassedEvent extends Event implements Cancellable {

    private final Level level;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;

    public LevelPassedEvent(@NotNull Level level){
        this.level = level;
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Level getLevel() {
        return this.level;
    }

}
