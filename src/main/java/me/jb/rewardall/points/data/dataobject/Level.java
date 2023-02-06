package me.jb.rewardall.points.data.dataobject;


public class Level {

    private final int level;
    private final MessageContent messageContent;

    public Level(int level, MessageContent messageContent) {
        this.level = level;
        this.messageContent = messageContent;
    }

    public int getLevel() {
        return this.level;
    }

    public MessageContent getMessageContent() {
        return this.messageContent;
    }

}
