package org.example.CommandStrategy.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.CommandStrategy.ICommand;


public class HelloCommand implements ICommand {
    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public String getDescription() {
        return "Say hello to the bot.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("👋 Hello " + event.getUser().getAsMention() + "!").setEphemeral(true).queue();
    }
}