package org.example.CommandStrategy;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private final Map<String, ICommand> commandMap = new HashMap<>();

    public void register(ICommand command) {
        commandMap.put(command.getName(), command);
    }

    public void execute(String name, SlashCommandInteractionEvent event) {
        ICommand command = commandMap.get(name);
        if (command != null) {
            command.execute(event);
        } else {
            event.reply("Unknown command: `" + name + "`").setEphemeral(true).queue();
        }
    }

    public Map<String, ICommand> getCommands() {
        return commandMap;
    }
}