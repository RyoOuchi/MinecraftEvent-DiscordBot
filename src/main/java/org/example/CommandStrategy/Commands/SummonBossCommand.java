package org.example.CommandStrategy.Commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.example.CommandStrategy.ICommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SummonBossCommand implements ICommand {
    public static final String BACKEND_URL = "https://minecraftevent-bossmod-backend.fly.dev";
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

        final String teamName = selectedRole.getName();
        System.out.println("Summoning boss for team: " + selectedRole.getName());

        String teamSendId = teamName.startsWith("Team ") ? teamName.substring(5) : "None";

        try {
            String apiUrl = BACKEND_URL + "/discord/summon-boss?teamName=" + teamSendId;

            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            System.out.println("GET /summon-boss returned: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            System.out.println("Response: " + content);

        } catch (Exception e) {
            System.err.println("❌ Error sending summon request: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
