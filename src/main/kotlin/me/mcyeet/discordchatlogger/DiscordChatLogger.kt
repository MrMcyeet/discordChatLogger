package me.mcyeet.discordchatlogger

import dev.dejvokep.boostedyaml.YamlDocument
import me.mcyeet.discordchatlogger.listeners.PlayerChatListener
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileNotFoundException

class DiscordChatLogger : JavaPlugin() {
    companion object {
        lateinit var Plugin: DiscordChatLogger
        lateinit var Config: YamlDocument
    }

    override fun onLoad() {
        val defaultConfig = getResource("config.yml") ?: throw FileNotFoundException("Default config is either missing or unreadable")
        Config = YamlDocument.create(File(dataFolder, "config.yml"), defaultConfig)
        Plugin = this
    }

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(PlayerChatListener(), Plugin)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}