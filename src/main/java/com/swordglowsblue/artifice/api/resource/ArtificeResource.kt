package com.swordglowsblue.artifice.api.resource

import com.google.gson.JsonElement
import com.swordglowsblue.artifice.api.resource.ArtificeResource
import com.google.gson.GsonBuilder
import java.io.ByteArrayInputStream
import java.util.HashMap
import com.swordglowsblue.artifice.api.resource.TemplateResource
import java.io.InputStream

/** A virtual resource file.  */
interface ArtificeResource<T> {
    /** @return The raw data contained by this resource file.
     */
    val data: T

    /** @return The output-formatted string representation of this resource's data.
     */
    fun toOutputString(): String

    /** @return This resource converted to an [InputStream].
     */
    fun toInputStream(): InputStream
}