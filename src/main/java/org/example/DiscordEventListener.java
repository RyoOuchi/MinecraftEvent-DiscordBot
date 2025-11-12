package org.example;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.example.CommandStrategy.CommandManager;
import org.example.CommandStrategy.CommandRegistry;
import org.jetbrains.annotations.NotNull;

public class DiscordEventListener extends ListenerAdapter {
    private final CommandManager commandManager;
    private final MinecraftBossFightBot bot;

    public DiscordEventListener(MinecraftBossFightBot bot) {
        this.bot = bot;
        this.commandManager = new CommandManager();
        CommandRegistry.registerAll(this.commandManager);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("onReady for " + event.getJDA().getSelfUser().getName());
        registerCommands(bot.getShardManager());
    }

    private void registerCommands(ShardManager jda) {
        Guild guild = jda.getGuildById("1437786444759437334");
        if (guild == null) {
            System.out.println("Guild not found! The bot might not be in that server.");
            return;
        }

        guild.updateCommands().addCommands(
                commandManager.getCommands().values().stream()
                        .map(cmd -> Commands.slash(cmd.getName(), cmd.getDescription())
                                .addOptions(cmd.getOptions()))
                        .toList()
        ).queue(
                success -> System.out.println("Commands registered: " + success.size()),
                error -> System.err.println("Failed to register commands: " + error.getMessage())
        );
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandManager.execute(event.getName(), event);
    }
}