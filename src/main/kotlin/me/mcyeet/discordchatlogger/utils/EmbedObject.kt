package me.mcyeet.discordchatlogger.utils

import me.mcyeet.discordchatlogger.utils.*
import java.awt.Color
import java.util.ArrayList

@Suppress("unused")
class EmbedObject {
    var title: String? = null
    var description: String? = null
    var url: String? = null
    var color: Color? = null
    var footer: DiscordWebhook.Footer? = null
    var thumbnail: DiscordWebhook.Thumbnail? = null
    var image: DiscordWebhook.Image? = null
    var author: DiscordWebhook.Author? = null
    val fields: MutableList<DiscordWebhook.Field> = ArrayList()

    fun setTitle(title: String?): EmbedObject {
        this.title = title
        return this
    }

    fun setDescription(description: String?): EmbedObject {
        this.description = description
        return this
    }

    fun setUrl(url: String): EmbedObject {
        this.url = url
        return this
    }

    fun setColor(color: String): EmbedObject {
        fun String.toColor(): Color? {
            if (!this.matches("[#][0-9a-fA-F]{6}".toRegex())) return null
            val digits: String = this.substring(1, this.length.coerceAtMost(7))
            val hxstr = "0x$digits"
            return Color.decode(hxstr)
        }
        this.color = color.toColor()
        return this
    }

    fun setColor(color: Color?): EmbedObject {
        this.color = color
        return this
    }

    fun setFooter(text: String, icon: String): EmbedObject {
        footer = DiscordWebhook.Footer(text, icon)
        return this
    }

    fun setThumbnail(url: String): EmbedObject {
        thumbnail = DiscordWebhook.Thumbnail(url)
        return this
    }

    fun setImage(url: String): EmbedObject {
        image = DiscordWebhook.Image(url)
        return this
    }

    fun setAuthor(name: String, url: String, icon: String): EmbedObject {
        author = DiscordWebhook.Author(name, url, icon)
        return this
    }

    fun addField(name: String, value: String, inline: Boolean): EmbedObject {
        fields.add(DiscordWebhook.Field(name, value, inline))
        return this
    }
}