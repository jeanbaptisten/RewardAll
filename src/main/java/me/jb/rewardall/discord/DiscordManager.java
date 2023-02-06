package me.jb.rewardall.discord;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import me.jb.rewardall.points.data.dataobject.MessageContent;
import org.jetbrains.annotations.NotNull;

public class DiscordManager {

    private final String textChannelId, specialMentionText;

    public DiscordManager(@NotNull String textChannelId, @NotNull String specialMentionText) {
        this.textChannelId = textChannelId;
        this.specialMentionText = specialMentionText;
    }

    public void sendEmbed(@NotNull MessageContent messageContent) {
        EmbedBuilder messageEmbed = messageContent.getEmbedBuilder();

        TextChannel textChannel = DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(textChannelId);

        if (textChannel == null)
            throw new IllegalStateException("TextChannel is not found !");

        if (messageContent.hasSpecialMention())
            textChannel.sendMessage(messageEmbed.build()).content(this.specialMentionText).queue();
        else
            textChannel.sendMessage(messageEmbed.build()).queue();
    }
}
