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
        println("Plugin loading")
        val defaultConfig = getResource("config.yml") ?: throw FileNotFoundException("Default config is either missing or unreadable")
        Config = YamlDocument.create(File(dataFolder, "config.yml"), defaultConfig)
        Plugin = this
        println("Plugin loaded")
    }

    override fun onEnable() {
        println("Plugin enabling")
        Bukkit.getPluginManager().registerEvents(PlayerChatListener(), Plugin)
        println("Plugin enabled")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}