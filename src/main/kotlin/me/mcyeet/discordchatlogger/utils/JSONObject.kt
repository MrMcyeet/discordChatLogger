package me.mcyeet.discordchatlogger.utils

import java.lang.reflect.Array

class JSONObject {
    private val map = HashMap<String, Any>()
    fun put(key: String, value: Any?) {
        if (value != null) {
            map[key] = value
        }
    }

    override fun toString(): String {
        val builder = StringBuilder()
        val entrySet: Set<Map.Entry<String, Any>> = map.entries
        builder.append("{")
        var i = 0
        entrySet.forEach { (key, value) ->
            builder.append(quote(key)).append(":")
            when (value) {
                is String -> builder.append(quote(value))
                is Int -> builder.append(value)
                is Boolean -> builder.append(value)
                is JSONObject -> builder.append(value.toString())
                javaClass.isArray -> {
                    builder.append("[")
                    val len = Array.getLength(value)
                    for (j in 0 until len) {
                        builder.append(Array.get(value, j).toString())
                            .append(if (j != len - 1) "," else "")
                    }
                    builder.append("]")
                }
            }
            builder.append(if (++i == entrySet.size) "}" else ",")
        }
        return builder.toString()
    }

    private fun quote(string: String): String {
        return "\"$string\""
    }
}