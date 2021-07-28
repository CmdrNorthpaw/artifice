package com.swordglowsblue.artifice.api.resource

import com.google.gson.JsonElement
import com.swordglowsblue.artifice.api.resource.ArtificeResource
import com.google.gson.GsonBuilder
import java.io.ByteArrayInputStream
import java.util.HashMap
import com.swordglowsblue.artifice.api.resource.TemplateResource
import java.io.InputStream

/** A virtual resource representing a JSON file.
 * @param <T> The specific type of [JsonElement] contained in this file (usually [JsonObject][com.google.gson.JsonObject])
</T> */
class JsonResource<T : JsonElement>
/** @param root The [JsonElement] this resource should wrap.
 */(override val data: T) : ArtificeResource<T> {
    override fun toOutputString(): String {
        return GsonBuilder().setPrettyPrinting().create().toJson(data)
    }

    override fun toInputStream(): InputStream {
        return ByteArrayInputStream(data.toString().toByteArray())
    }
}