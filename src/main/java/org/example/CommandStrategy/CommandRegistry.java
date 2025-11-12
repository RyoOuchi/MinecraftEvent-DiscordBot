package org.example.CommandStrategy;

import org.example.CommandStrategy.Commands.HelloCommand;
import org.example.CommandStrategy.Commands.PingCommand;
import org.example.CommandStrategy.Commands.SummonBossCommand;

public class CommandRegistry {
    public static void registerAll(CommandManager manager) {
        manager.register(new HelloCommand());
        manager.register(new PingCommand());
        manager.register(new SummonBossCommand());
    }
}