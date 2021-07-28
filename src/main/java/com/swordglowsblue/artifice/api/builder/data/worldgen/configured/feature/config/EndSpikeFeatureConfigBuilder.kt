package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.feature.config

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

class EndSpikeFeatureConfigBuilder : FeatureConfigBuilder() {
    fun crystalInvulnerable(crystalInvulnerable: Boolean): EndSpikeFeatureConfigBuilder {
        this.root.addProperty("crystal_invulnerable", crystalInvulnerable)
        return this
    }

    fun crystalBeamTarget(x: Int, y: Int, z: Int): EndSpikeFeatureConfigBuilder {
        this.root.add("crystal_beam_target", JsonArray())
        this.root.getAsJsonArray("crystal_beam_target").add(x)
        this.root.getAsJsonArray("crystal_beam_target").add(y)
        this.root.getAsJsonArray("crystal_beam_target").add(z)
        return this
    }

    fun addSpike(processor: SpikeBuilder.() -> Unit): EndSpikeFeatureConfigBuilder {
        this.root.getAsJsonArray("spikes").add(SpikeBuilder().apply(processor).buildTo(JsonObject()))
        return this
    }

    class SpikeBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun centerX(centerX: Int): SpikeBuilder {
            this.root.addProperty("centerX", centerX)
            return this
        }

        fun centerZ(centerZ: Int): SpikeBuilder {
            this.root.addProperty("centerZ", centerZ)
            return this
        }

        fun radius(radius: Int): SpikeBuilder {
            this.root.addProperty("radius", radius)
            return this
        }

        fun height(height: Int): SpikeBuilder {
            this.root.addProperty("height", height)
            return this
        }

        fun guarded(guarded: Boolean): SpikeBuilder {
            this.root.addProperty("guarded", guarded)
            return this
        }
    }

    init {
        this.root.add("spikes", JsonArray())
    }
}