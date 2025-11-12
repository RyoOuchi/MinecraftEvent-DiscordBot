package org.example.CommandStrategy.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.CommandStrategy.ICommand;

public class PingCommand implements ICommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Check the bot's latency.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        long latency = System.currentTimeMillis() - event.getTimeCreated().toInstant().toEpochMilli();
        event.reply("Pong! `" + latency + "ms`").queue();
    }
}