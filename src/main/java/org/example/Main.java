package org.example;

public class Main {
    public static void main(String[] args) {

        String token = System.getenv("DISCORD_TOKEN");

        if (token == null || token.isBlank()) {
            System.err.println("ERROR: No Discord token provided.");
            System.err.println("Set the DISCORD_TOKEN environment variable before running.");
            System.err.println("Example:");
            System.err.println("  export DISCORD_TOKEN=\"YOUR_TOKEN_HERE\"");
            System.exit(1);
        }

        MinecraftBossFightBot.selfBot = new MinecraftBossFightBot(token);
    }
}