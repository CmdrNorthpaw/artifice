package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.ChunkGeneratorTypeBuilder.FlatChunkGeneratorTypeBuilder
import com.swordglowsblue.artifice.api.builder.data.dimension.ChunkGeneratorTypeBuilder.NoiseChunkGeneratorTypeBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.Builder
import net.minecraft.util.Identifier
import java.util.function.Function

class DimensionBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    Function<JsonObject, JsonResource<JsonObject>> { root: JsonObject -> JsonResource(root) }) {
    /**
     * Set the dimension type.
     * @param identifier
     * @return
     */
    fun dimensionType(identifier: Identifier): DimensionBuilder {
        this.root.addProperty("type", identifier.toString())
        return this
    }

    /**
     * Make a Chunk Generator.
     * @param instance
     * @param builder
     * @param <T> A class extending ChunkGeneratorTypeBuilder.
     * @return
    </T> */
    fun <T : ChunkGeneratorTypeBuilder> generator(
        instance: T,
        builder: Builder<T>
    ): DimensionBuilder {
        with("generator", { JsonObject() }) { generator: JsonObject ->
            instance.apply(builder).buildTo(generator)
        }
        return this
    }

    /**
     * Make a noise based Chunk Generator.
     * @param generatorBuilder
     * @return
     */
    fun noiseGenerator(generatorBuilder: Builder<NoiseChunkGeneratorTypeBuilder>): DimensionBuilder {
        return generator(NoiseChunkGeneratorTypeBuilder(), generatorBuilder)
    }

    /**
     * Make a flat Chunk Generator.
     * @param generatorBuilder
     * @return
     */
    fun flatGenerator(generatorBuilder: Builder<FlatChunkGeneratorTypeBuilder>): DimensionBuilder {
        return generator(FlatChunkGeneratorTypeBuilder(), generatorBuilder)
    }

    /**
     * Use with a Chunk Generator which doesn't need any configuration. Example: Debug Mode Generator.
     * @param generatorId The ID of the chunk generator type.
     * @return this
     */
    fun simpleGenerator(generatorId: String): DimensionBuilder {
        val jsonObject = JsonObject()
        jsonObject.addProperty("type", generatorId)
        this.root.add("generator", jsonObject)
        return this
    }
}