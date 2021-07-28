package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FeatureSizeBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.FoliagePlacerBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TreeDecoratorBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.TrunkPlacerBuilder
import net.minecraft.world.Heightmap

class TreeFeatureConfigBuilder : FeatureConfigBuilder() {
    fun maxWaterDepth(maxWaterDepth: Int): TreeFeatureConfigBuilder {
        this.root.addProperty("max_water_depth", maxWaterDepth)
        return this
    }

    fun ignoreVines(ignoreVines: Boolean): TreeFeatureConfigBuilder {
        this.root.addProperty("ignore_vines", ignoreVines)
        return this
    }

    fun <P : BlockStateProviderBuilder> trunkProvider(
        instance: P,
        providerProcessor: P.() -> Unit
    ): TreeFeatureConfigBuilder {
        with("trunk_provider", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(providerProcessor).buildTo(jsonObject)
        }
        return this
    }

    fun <P : BlockStateProviderBuilder> leavesProvider(
        instance: P,
        providerProcessor: P.() -> Unit
    ): TreeFeatureConfigBuilder {
        with("leaves_provider", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(providerProcessor).buildTo(jsonObject)
        }
        return this
    }

    fun <P : FoliagePlacerBuilder<*>> foliagePlacer(
        instance: P,
        providerProcessor: P.() -> Unit
    ): TreeFeatureConfigBuilder {
        with("foliage_placer", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(providerProcessor).buildTo(jsonObject)
        }
        return this
    }

    fun <P : TrunkPlacerBuilder<*>> trunkPlacer(
        instance: P,
        providerProcessor: P.() -> Unit
    ): TreeFeatureConfigBuilder {
        with("trunk_placer", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(providerProcessor).buildTo(jsonObject)
        }
        return this
    }

    fun <P : FeatureSizeBuilder> minimumSize(instance: P, providerProcessor: P.() -> Unit): TreeFeatureConfigBuilder {
        with("minimum_size", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(providerProcessor).buildTo(jsonObject)
        }
        return this
    }

    fun <D : TreeDecoratorBuilder> addDecorator(instance: D, processor: D.() -> Unit): TreeFeatureConfigBuilder {
        this.root.getAsJsonArray("decorators")
            .add(instance.apply(processor).buildTo(JsonObject()))
        return this
    }

    fun heightmap(type: Heightmap.Type): TreeFeatureConfigBuilder {
        this.root.addProperty("heightmap", type.getName())
        return this
    }

    init {
        this.root.add("decorators", JsonArray())
    }
}