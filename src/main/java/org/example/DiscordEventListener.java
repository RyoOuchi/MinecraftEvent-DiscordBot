package org.example;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;

public class DiscordEventListener extends ListenerAdapter {

    private final MinecraftBossFightBot bot;

    public DiscordEventListener(MinecraftBossFightBot bot) {
        this.bot = bot;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("✅ onReady triggered for " + event.getJDA().getSelfUser().getName());
        registerCommands(bot.getShardManager());
    }

    private void registerCommands(ShardManager jda) {
        Guild g = jda.getGuildById("1437786444759437334");
        if (g == null) {
            System.out.println("⚠️ Guild not found! The bot might not be in that server.");
            return;
        }

        System.out.println("✅ Found guild: " + g.getName());
        g.updateCommands()
                .addCommands(Commands.slash("hello", "Say hello"))
                .queue(
                        success -> System.out.println("✅ Slash command registered successfully!"),
                        error -> System.err.println("❌ Failed to register command: " + error)
                );
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("hello")) {
            event.reply("Hello " + event.getUser().getAsMention() + "!")
                    .setEphemeral(true)
                    .queue();
        }
    }
}