package com.swordglowsblue.artifice.api.builder.data.worldgen.gen

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.builder.data.worldgen.BlockStateProviderBuilder
import net.minecraft.util.Identifier
import java.util.function.Function

sealed class TreeDecoratorBuilder(
    type: Identifier
) : TypedJsonBuilder<JsonObject?>(JsonObject(), Function { j: JsonObject? -> j }) {
    init {
        root.addProperty("type", type.toString())
    }

    class TrunkVineTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("trunk_vine"))

    class LeaveVineTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("leave_vine"))

    class CocoaTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("cocoa")) {
        fun probability(probability: Float): CocoaTreeDecoratorBuilder {
            require(probability <= 1.0f) { "probability can't be higher than 1.0F! Found $probability" }
            require(probability >= 0.0f) { "probability can't be smaller than 0.0F! Found $probability" }
            this.root.addProperty("probability", probability)
            return this
        }
    }

    class BeeHiveTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("beehive")) {
        fun probability(probability: Float): BeeHiveTreeDecoratorBuilder {
            require(probability <= 1.0f) { "probability can't be higher than 1.0F! Found $probability" }
            require(probability >= 0.0f) { "probability can't be smaller than 0.0F! Found $probability" }
            this.root.addProperty("probability", probability)
            return this
        }
    }

    class AlterGroundTreeDecoratorBuilder : TreeDecoratorBuilder(Identifier("alter_ground")) {
        fun <P : BlockStateProviderBuilder> provider(
            instance: P,
            processor: P.() -> Unit
        ): AlterGroundTreeDecoratorBuilder {
            with("provider", { JsonObject() }) { jsonObject: JsonObject? ->
                instance.apply(processor).buildTo(jsonObject)
            }
            return this
        }
    }
}