package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.JsonObjectBuilder
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.ChunkGeneratorTypeBuilder.FlatChunkGeneratorTypeBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.ChunkGeneratorTypeBuilder.NoiseChunkGeneratorTypeBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.api.util.IdUtils.addProperty
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import net.minecraft.util.Identifier
import java.util.function.Function

@ArtificeDsl
class DimensionBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }
) {
    /**
     * Set the dimension type.
     * @param identifier
     * @return
     */
    fun dimensionType(identifier: Identifier) = apply { dimensionType = identifier }

    var dimensionType: Identifier?
    get() = root["type"].asId
    set(value) = root.addProperty("type", value)

    /**
     * Make a Chunk Generator.
     * @param instance
     * @param builder
     * @param <T> A class extending ChunkGeneratorTypeBuilder.
     * @return
    </T> */
    fun <T : ChunkGeneratorTypeBuilder> generator(instance: T, builder: Builder<T>) = apply {
        with("generator", { JsonObject() }) { generator: JsonObject ->
            instance.apply(builder).buildTo(generator)
        }
    }

    /**
     * Make a noise based Chunk Generator.
     * @param generatorBuilder
     * @return
     */
    fun noiseGenerator(generatorBuilder: Builder<NoiseChunkGeneratorTypeBuilder>) = apply {
        return generator(NoiseChunkGeneratorTypeBuilder(), generatorBuilder)
    }

    /**
     * Make a flat Chunk Generator.
     * @param generatorBuilder
     * @return
     */
    fun flatGenerator(generatorBuilder: Builder<FlatChunkGeneratorTypeBuilder>) =
         generator(FlatChunkGeneratorTypeBuilder(), generatorBuilder)

    /**
     * Use with a Chunk Generator which doesn't need any configuration. Example: Debug Mode Generator.
     * @param generatorId The ID of the chunk generator type.
     * @return this
     */
    fun simpleGenerator(generatorId: Identifier) = apply {
        val generatorJson = JsonObjectBuilder().add("type", generatorId.toString()).build()
        root.add("generator", generatorJson)
    }
}