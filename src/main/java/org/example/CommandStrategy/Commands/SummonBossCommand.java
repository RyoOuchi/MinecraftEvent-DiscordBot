package org.example.CommandStrategy.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.CommandStrategy.ICommand;

import java.util.List;

public class SummonBossCommand implements ICommand {
    @Override
    public String getName() {
        return "summon";
    }

    @Override
    public String getDescription() {
        return "ボスをマインクラフトのワールド内に召喚します。";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.ROLE, "チーム", "絶対他のチームを記入しないでね！").setRequired(true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var selectedRole = event.getOption("チーム").getAsRole();
        var member = event.getMember();

        if (member == null) {
            event.reply("このコマンドはサーバー内でのみ使用できます。").setEphemeral(true).queue();
            return;
        }

        boolean hasRole = member.getRoles().stream()
                .anyMatch(role -> role.getIdLong() == selectedRole.getIdLong());

        if (!hasRole) {
            event.reply("あなたの所属チームと一致しません！").setEphemeral(true).queue();
            return;
        }

        event.reply("チームが確認されました！ボスを召喚します…").queue();

        // TODO: Trigger your Minecraft summon logic here
        final String teamName = selectedRole.getName();
        System.out.println("Summoning boss for team: " + selectedRole.getName());

        String teamSendId = teamName.startsWith("Team ") ? teamName.substring(5) : "None";


    }
}
