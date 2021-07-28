package com.swordglowsblue.artifice.api.builder.data.worldgen.biome

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import java.util.function.Function

class BiomeEffectsBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
    /**
     * @param fog_color RGB value.
     * @return BiomeBuilder
     */
    fun fogColor(fog_color: Int): BiomeEffectsBuilder {
        this.root.addProperty("fog_color", fog_color)
        return this
    }

    /**
     * @param water_color RGB value.
     * @return BiomeBuilder
     */
    fun waterColor(water_color: Int): BiomeEffectsBuilder {
        this.root.addProperty("water_color", water_color)
        return this
    }

    /**
     * @param sky_color RGB value.
     * @return BiomeBuilder
     */
    fun skyColor(sky_color: Int): BiomeEffectsBuilder {
        this.root.addProperty("sky_color", sky_color)
        return this
    }

    /**
     * @param foliage_color RGB value.
     * @return BiomeBuilder
     */
    fun foliageColor(foliage_color: Int): BiomeEffectsBuilder {
        this.root.addProperty("foliage_color", foliage_color)
        return this
    }

    /**
     * @param grass_color RGB value.
     * @return BiomeBuilder
     */
    fun grassColor(grass_color: Int): BiomeEffectsBuilder {
        this.root.addProperty("grass_color", grass_color)
        return this
    }

    /**
     * @param grass_color_modifier RGB value.
     * @return BiomeBuilder
     */
    fun grassColorModifier(grass_color_modifier: Int): BiomeEffectsBuilder {
        this.root.addProperty("grass_color_modifier", grass_color_modifier)
        return this
    }

    /**
     * @param water_fog_color RGB value.
     * @return BiomeBuilder
     */
    fun waterFogColor(water_fog_color: Int): BiomeEffectsBuilder {
        this.root.addProperty("water_fog_color", water_fog_color)
        return this
    }

    fun ambientSound(soundID: String): BiomeEffectsBuilder {
        this.root.addProperty("ambient_sound", soundID)
        return this
    }

    fun moodSound(biomeMoodSoundBuilder: BiomeMoodSoundBuilder.() -> Unit): BiomeEffectsBuilder {
        with("mood_sound", { JsonObject() }) { biomeMoodSound: JsonObject ->
            BiomeMoodSoundBuilder().apply(biomeMoodSoundBuilder).buildTo(biomeMoodSound)
        }
        return this
    }

    fun additionsSound(biomeAdditionsSoundBuilder: BiomeAdditionsSoundBuilder.() -> Unit): BiomeEffectsBuilder {
        with(
            "additions_sound",
            { JsonObject() }) { biomeAdditionsSound: JsonObject ->
            BiomeAdditionsSoundBuilder().apply(biomeAdditionsSoundBuilder).buildTo(biomeAdditionsSound)
        }
        return this
    }

    fun music(biomeMusicSoundBuilder: BiomeMusicSoundBuilder.() -> Unit): BiomeEffectsBuilder {
        with("music", { JsonObject() }) { biomeMusicSound: JsonObject ->
            BiomeMusicSoundBuilder().apply(biomeMusicSoundBuilder).buildTo(biomeMusicSound)
        }
        return this
    }

    fun particle(biomeParticleConfigBuilder: BiomeParticleConfigBuilder.() -> Unit): BiomeEffectsBuilder {
        with("particle", { JsonObject() }) { biomeParticleConfig: JsonObject ->
            BiomeParticleConfigBuilder().apply(biomeParticleConfigBuilder).buildTo(biomeParticleConfig)
        }
        return this
    }

    class BiomeMoodSoundBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun tickDelay(tick_delay: Int): BiomeMoodSoundBuilder {
            this.root.addProperty("tick_delay", tick_delay)
            return this
        }

        fun blockSearchExtent(block_search_extent: Int): BiomeMoodSoundBuilder {
            this.root.addProperty("block_search_extent", block_search_extent)
            return this
        }

        fun offset(offset: Double): BiomeMoodSoundBuilder {
            this.root.addProperty("offset", offset)
            return this
        }

        fun soundID(soundID: String): BiomeMoodSoundBuilder {
            this.root.addProperty("sound", soundID)
            return this
        }
    }

    class BiomeMusicSoundBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun minDelay(min_delay: Int): BiomeMusicSoundBuilder {
            this.root.addProperty("min_delay", min_delay)
            return this
        }

        fun maxDelay(max_delay: Int): BiomeMusicSoundBuilder {
            this.root.addProperty("max_delay", max_delay)
            return this
        }

        fun replaceCurrentMusic(replace_current_music: Boolean): BiomeMusicSoundBuilder {
            this.root.addProperty("replace_current_music", replace_current_music)
            return this
        }

        fun soundID(soundID: String): BiomeMusicSoundBuilder {
            this.root.addProperty("sound", soundID)
            return this
        }
    }

    class BiomeAdditionsSoundBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun tickChance(tick_chance: Double): BiomeAdditionsSoundBuilder {
            this.root.addProperty("tick_chance", tick_chance)
            return this
        }

        fun soundID(soundID: String): BiomeAdditionsSoundBuilder {
            this.root.addProperty("sound", soundID)
            return this
        }
    }

    class BiomeParticleConfigBuilder : TypedJsonBuilder<JsonObject>(JsonObject(), { j: JsonObject -> j }) {
        fun probability(probability: Float): BiomeParticleConfigBuilder {
            this.root.addProperty("probability", probability)
            return this
        }

        fun particleID(id: String): BiomeParticleConfigBuilder {
            val jsonObject = JsonObject()
            jsonObject.addProperty("type", id)
            this.root.add("options", jsonObject)
            return this
        }
    }
}