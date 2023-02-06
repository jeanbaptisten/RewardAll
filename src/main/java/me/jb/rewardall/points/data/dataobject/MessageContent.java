package me.jb.rewardall.points.data.dataobject;

import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;

public class MessageContent {

    private final EmbedBuilder embedBuilder;
    private final boolean specialMention;

    public MessageContent(EmbedBuilder embedBuilder, boolean specialMention) {
        this.embedBuilder = embedBuilder;
        this.specialMention = specialMention;
    }

    public EmbedBuilder getEmbedBuilder() {
        return this.embedBuilder;
    }

    public boolean hasSpecialMention() {
        return this.specialMention;
    }
}
