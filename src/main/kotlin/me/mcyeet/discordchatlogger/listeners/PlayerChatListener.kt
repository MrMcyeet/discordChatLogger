package me.mcyeet.discordchatlogger.listeners

import me.mcyeet.discordchatlogger.DiscordChatLogger.Companion.Config
import me.mcyeet.discordchatlogger.utils.DiscordWebhook
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class PlayerChatListener: Listener {

    @EventHandler
    fun onPlayerChat(event: AsyncPlayerChatEvent) {
        println("event triggered")
        //if (event.player.hasPermission("discordchatlogger.nolog")) return

        val formattedMessage = event.message
            .replace("_", "\\\\_")
            .replace("*", "\\\\*")
            .replace("|", "\\\\|")
            .replace("http", "http\u200B")

        DiscordWebhook(Config.getString("Webhook_URL"))
            .setContent(formattedMessage)
            .setUsername(event.player.displayName)
            .setAvatarUrl("https://crafatar.com/renders/head/${event.player.uniqueId}")
            .execute()
        println("event finished")
    }

}