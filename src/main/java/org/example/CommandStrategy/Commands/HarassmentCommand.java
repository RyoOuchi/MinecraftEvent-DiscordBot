package org.example.CommandStrategy.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.CommandStrategy.ICommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.example.CommandStrategy.Commands.SummonBossCommand.BACKEND_URL;

public class HarassmentCommand implements ICommand {

    @Override
    public String getName() {
        return "harass";
    }

    @Override
    public String getDescription() {
        return "他のチームに嫌がらせしましょう！";
    }

    @Override
    public List<OptionData> getOptions() {
        return List.of(
                new OptionData(OptionType.ROLE, "チーム", "嫌がらせするチームを選んでね！").setRequired(true)
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

        if (hasRole) {
            event.reply("所属チームに嫌がらせできません！").setEphemeral(true).queue();
            return;
        }

        event.reply("チームが確認されました！" + selectedRole.getName() + "に嫌がらせをします！").queue();

        final String teamName = selectedRole.getName();
        System.out.println("Summoning boss for team: " + selectedRole.getName());

        String teamSendId = teamName.startsWith("Team ") ? teamName.substring(5) : "None";

        try {
            String apiUrl = BACKEND_URL + "/discord/harass?teamName=" + teamSendId;

            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            System.out.println("GET /harass returned: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + content);

        } catch (Exception e) {
            System.err.println("❌ Error sending harass request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
