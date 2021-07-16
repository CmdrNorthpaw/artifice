package com.swordglowsblue.artifice.api.resource

import com.google.gson.JsonElement
import com.swordglowsblue.artifice.api.resource.ArtificeResource
import com.google.gson.GsonBuilder
import java.io.ByteArrayInputStream
import java.util.HashMap
import com.swordglowsblue.artifice.api.resource.TemplateResource
import java.io.InputStream

/** A virtual resource representing an arbitrary string.  */
class StringResource(vararg lines: String) : ArtificeResource<String> {
    override val data: String = lines.joinToString("\n")
    override fun toOutputString(): String {
        return data
    }

    override fun toInputStream(): InputStream {
        return ByteArrayInputStream(data.toByteArray())
    }

}