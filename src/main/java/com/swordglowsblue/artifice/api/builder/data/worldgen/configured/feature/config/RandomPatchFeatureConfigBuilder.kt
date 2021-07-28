package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.data.StateDataBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.gen.BlockPlacerBuilder

class RandomPatchFeatureConfigBuilder : FeatureConfigBuilder() {
    fun <P : BlockStateProviderBuilder> stateProvider(
        instance: P,
        processor: P.() -> Unit
    ): RandomPatchFeatureConfigBuilder {
        with("state_provider", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun <P : BlockPlacerBuilder> blockPlacer(instance: P, processor: P.() -> Unit): RandomPatchFeatureConfigBuilder {
        with("block_placer", { JsonObject() }) { jsonObject: JsonObject ->
            instance.apply(processor).buildTo(jsonObject)
        }
        return this
    }

    fun addBlockToWhitelist(processor: StateDataBuilder.() -> Unit): RandomPatchFeatureConfigBuilder {
        this.root.getAsJsonArray("whitelist")
            .add(StateDataBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    fun addBlockToBlacklist(processor: StateDataBuilder.() -> Unit): RandomPatchFeatureConfigBuilder {
        this.root.getAsJsonArray("blacklist")
            .add(StateDataBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    fun tries(tries: Int): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("tries", tries)
        return this
    }

    fun xSpread(xSpread: Int): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("xspread", xSpread)
        return this
    }

    fun ySpread(ySpread: Int): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("yspread", ySpread)
        return this
    }

    fun zSpread(zSpread: Int): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("zspread", zSpread)
        return this
    }

    fun canReplace(canReplace: Boolean): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("can_replace", canReplace)
        return this
    }

    fun project(project: Boolean): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("project", project)
        return this
    }

    fun needWater(needWater: Boolean): RandomPatchFeatureConfigBuilder {
        this.root.addProperty("need_water", needWater)
        return this
    }

    init {
        this.root.add("whitelist", JsonArray())
        this.root.add("blacklist", JsonArray())
    }
}