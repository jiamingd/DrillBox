import com.example.jyu.drillbox.utils.EnumUtils
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject

fun jsonObject(vararg params: Pair<String, Any?>, includeNulls: Boolean = true): JsonObject {
    val json = JsonObject()
    params.forEach {
        val key = it.first
        val value = it.second
        when (value) {
            null -> if (includeNulls) json.add(key, JsonNull.INSTANCE)
            is String -> json.addProperty(key, value)
            is Number -> json.addProperty(key, value)
            is Boolean -> json.addProperty(key, value)
            is Enum<*> -> json.addProperty(key, value.serializedName)
            is List<*> -> json.add(key, jsonArray(*value.toTypedArray()))
            is JsonElement -> json.add(key, value)
        // feel free to add things in here
            else -> throw RuntimeException("bad type ${value::class}")
        }
    }
    return json
}

fun jsonArray(vararg entries: Any?): JsonArray {
    val json = JsonArray()
    entries.forEach {
        when (it) {
            null -> json.add(JsonNull.INSTANCE)
            is String -> json.add(it)
            is Boolean -> json.add(it)
            is Number -> json.add(it)
            is JsonElement -> json.add(it)
            is Enum<*> -> json.add(it.serializedName)
        // feel free to add things in here
            else -> throw RuntimeException("bad type ${it::class}")
        }
    }
    return json
}

val Enum<*>?.serializedName: String?
    get() = EnumUtils.getSerializedName(this)
