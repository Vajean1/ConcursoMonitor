package com.vajean.concurso_monitor;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class DiscordBot {
    public static void main(String[] args) {

        //Login Discord Bot
        System.out.println("Starting Discord Bot...");

        final String token = "MTQwOTU1Nzg4NjA3NzUwNTczOA.GGtD88.rl0Hu00fLlGzwc6zMF8KLk7AX7sXCqVZKaQ0JQ";
        //System.getenv("DISCORD_BOT_TOKEN");

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

        client.onDisconnect().block();
        System.out.println("Discord Bot started.");


        client.getEventDispatcher().on(MessageCreateEvent.class)
            .flatMap(event -> Mono.just(event.getMessage().getContent())
            .filter(content -> content.startsWith("!hello"))
            .flatMap(content -> event.getMessage().getChannel())
            .flatMap(channel -> channel.createMessage("Hello World!"))
        ).subscribe();
    }
}
