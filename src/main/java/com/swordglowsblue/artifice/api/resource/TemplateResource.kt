package com.swordglowsblue.artifice.api.resource

import com.google.gson.JsonElement
import com.swordglowsblue.artifice.api.resource.ArtificeResource
import com.google.gson.GsonBuilder
import java.io.ByteArrayInputStream
import java.util.HashMap
import com.swordglowsblue.artifice.api.resource.TemplateResource
import java.io.InputStream

/** A virtual resource representing a string with template expansions.
 * Templates in the string take the form `$key`.
 * @see TemplateResource.expand
 */
class TemplateResource(vararg template: String) : ArtificeResource<String> {
    private val template: String = template.joinToString("\n")
    private val expansions: MutableMap<String, String> = HashMap()

    /**
     * Set the expansion string for a given key.
     * @param key The key to be expanded (ex. `"key"` expands `$key`).
     * @param expansion The expanded string.
     * @return this
     */
    fun expand(key: String, expansion: String): TemplateResource {
        expansions[key] = expansion
        return this
    }

    override fun toInputStream(): InputStream {
        return ByteArrayInputStream(data.toByteArray())
    }

    override fun toOutputString(): String {
        return data
    }

    override val data: String
        get() {
            var expanded = template
            for (key in expansions.keys) expanded = expanded.replace("\\$" + key.toRegex(), expansions[key]!!)
            return expanded
        }


}