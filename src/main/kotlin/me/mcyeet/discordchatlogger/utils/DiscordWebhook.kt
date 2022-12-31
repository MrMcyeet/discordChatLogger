package me.mcyeet.discordchatlogger.utils

import me.mcyeet.discordchatlogger.utils.*
import java.io.IOException
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Class used to execute Discord Webhooks with low effort
 *
 * Kotlinified version of https://gist.github.com/k3kdude/fba6f6b37594eae3d6f9475330733bdb
 */
@Suppress("unused")
class DiscordWebhook(private val url: String) {

    data class Footer(val text: String, val iconUrl: String)

    data class Thumbnail(val url: String)

    data class Image(val url: String)

    data class Author(
        val name: String,
        val url: String,
        val iconUrl: String
    )

    data class Field(
        val name: String,
        val value: String,
        val isInline: Boolean
    )

    private var content: String? = null
    private var username: String? = null
    private var avatarUrl: String? = null
    private var tts = false
    private val embeds: MutableList<EmbedObject> = ArrayList()

    fun setContent(content: String?) = apply { this.content = content }

    fun setUsername(username: String?) = apply { this.username = username }

    fun setAvatarUrl(avatarUrl: String?) = apply { this.avatarUrl = avatarUrl }

    fun setTts(tts: Boolean) = apply { this.tts = tts }

    fun addEmbed(embed: EmbedObject) = apply { embeds.add(embed) }

    @Throws(IOException::class)
    fun execute() {
        if (content == null && embeds.isEmpty()) {
            throw IllegalArgumentException("Set content or add at least one EmbedObject")
        }
        val json = JSONObject()
        json.put("content", content)
        json.put("username", username)
        json.put("avatar_url", avatarUrl)
        json.put("tts", tts)
        if (embeds.isNotEmpty()) {
            val embedObjects: MutableList<JSONObject> = ArrayList()
            for (embed: EmbedObject in embeds) {
                val jsonEmbed = JSONObject()
                jsonEmbed.put("title", embed.title)
                jsonEmbed.put("description", embed.description)
                jsonEmbed.put("url", embed.url)
                val color = embed.color
                if (color != null) {
                    var rgb = color.red
                    rgb = (rgb shl 8) + color.green
                    rgb = (rgb shl 8) + color.blue
                    jsonEmbed.put("color", rgb)
                }
                val footer: Footer? = embed.footer
                val image = embed.image
                val thumbnail: Thumbnail? = embed.thumbnail
                val author: Author? = embed.author
                val fields = embed.fields
                if (footer != null) {
                    val jsonFooter = JSONObject()
                    jsonFooter.put("text", footer.text)
                    jsonFooter.put("icon_url", footer.iconUrl)
                    jsonEmbed.put("footer", jsonFooter)
                }
                if (image != null) {
                    val jsonImage = JSONObject()
                    jsonImage.put("url", image.url)
                    jsonEmbed.put("image", jsonImage)
                }
                if (thumbnail != null) {
                    val jsonThumbnail = JSONObject()
                    jsonThumbnail.put("url", thumbnail.url)
                    jsonEmbed.put("thumbnail", jsonThumbnail)
                }
                if (author != null) {
                    val jsonAuthor = JSONObject()
                    jsonAuthor.put("name", author.name)
                    jsonAuthor.put("url", author.url)
                    jsonAuthor.put("icon_url", author.iconUrl)
                    jsonEmbed.put("author", jsonAuthor)
                }
                val jsonFields: MutableList<JSONObject> = ArrayList()
                for (field in fields) {
                    val jsonField = JSONObject()
                    jsonField.put("name", field.name)
                    jsonField.put("value", field.value)
                    jsonField.put("inline", field.isInline)
                    jsonFields.add(jsonField)
                }
                jsonEmbed.put("fields", jsonFields.toTypedArray())
                embedObjects.add(jsonEmbed)
            }
            json.put("embeds", embedObjects.toTypedArray())
        }
        val url = URL(url)
        val connection = url.openConnection() as HttpsURLConnection
        connection.addRequestProperty("Content-Type", "application/json")
        connection.addRequestProperty("User-Agent", "spigot-plugin")
        connection.doOutput = true
        connection.requestMethod = "POST"
        val stream = connection.outputStream
        stream.write(json.toString().toByteArray(Charsets.UTF_8))
        stream.flush()
        stream.close()
        connection.inputStream.close() //I'm not sure why, but it doesn't work without getting the InputStream
        connection.disconnect()
    }
}