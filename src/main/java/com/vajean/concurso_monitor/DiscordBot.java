package com.vajean.concurso_monitor;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import discord4j.core.event.domain.message.MessageCreateEvent;
import com.austinv11.servicer.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

@Service
public class DiscordBot {
    public static void StartBot() {

        //Login Discord Bot
        System.out.println("Starting Discord Bot...");

        final String token = System.getenv("DISCORD_BOT_TOKEN");

        if (token == null || token.isEmpty()) {
            System.err.println("Error: DISCORD_BOT_TOKEN environment variable is not set.");
            return;
        }

        final GatewayDiscordClient client = DiscordClientBuilder.create(token)
                .build()
                .login()
                .block();

        if (client == null) {
            System.err.println("Error: Failed to create Discord client. Verify the bot token.");
            return;
        }

        client.getEventDispatcher().on(ReadyEvent.class).subscribe(
            event -> {
                final User bot = event.getSelf();
                System.out.println(String.format("Logged in as %s#%s", bot.getUsername(), bot.getDiscriminator()));
            }
        );
        
        System.out.println("Discord Bot started.");

        client.getEventDispatcher().on(MessageCreateEvent.class)
        .subscribe(event -> {
        final String content = event.getMessage().getContent();
        if (event.getMessage().getAuthor().map(User::isBot).orElse(true)) return;
        if (content.equals("!abertos")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Concursos[] concursos = mapper.readValue(new File("concursos.json"), Concursos[].class);
                StringBuilder response = new StringBuilder("Concursos Abertos:\n");
                for (Concursos concurso : concursos) {
                    if (concurso.getConcurso_status().equalsIgnoreCase("aberto")) {
                        response.append(String.format("- %s (%s): Vagas: %s\n", concurso.getConcurso_name(), concurso.getConcurso_link(), concurso.getConcurso_qnt()));
                    }
                }
                    event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
            } catch (Exception e) {
                e.printStackTrace();
                event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("error to read concursos.json"))
                    .subscribe();
            }
        } else if (content.equals("!previstos")) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Concursos[] concursos = mapper.readValue(new File("concursos.json"), Concursos[].class);
                StringBuilder response = new StringBuilder("Concursos Previstos:\n");
                for (Concursos concurso : concursos) {
                    if (concurso.getConcurso_status().equalsIgnoreCase("previsto")) {
                        response.append(String.format("- %s (%s): Vagas: %s\n", concurso.getConcurso_name(), concurso.getConcurso_link(), concurso.getConcurso_qnt()));
                    }
                }
                    event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
            } catch (Exception e) {
                e.printStackTrace();
                event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("error to read concursos.json"))
                    .subscribe();
            }
            
        } else if (content.equals("!concursos")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                Concursos[] concursos = mapper.readValue(new File("concursos.json"), Concursos[].class);
                StringBuilder response = new StringBuilder("Todos os Concursos:\n");
                for (Concursos concurso : concursos) {
                    response.append(String.format("- %s (%s): Vagas: %s - Status: %s\n", concurso.getConcurso_name(), concurso.getConcurso_link(), concurso.getConcurso_qnt(), concurso.getConcurso_status()));
                }
                    event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage(response.toString()))
                    .subscribe();
            } catch (Exception e) {
                e.printStackTrace();
                event.getMessage().getChannel()
                    .flatMap(channel -> channel.createMessage("error to read concursos.json"))
                    .subscribe();
            }
        }
     });
        client.onDisconnect().block();
    
    }
}
