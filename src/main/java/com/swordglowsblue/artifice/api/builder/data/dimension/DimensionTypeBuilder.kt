package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.minecraft.util.Identifier
import java.util.function.Function

class DimensionTypeBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    Function<JsonObject, JsonResource<JsonObject>> { root: JsonObject -> JsonResource(root) }) {
    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param ultrawarm
     * @return this
     */
    fun ultrawarm(ultrawarm: Boolean): DimensionTypeBuilder {
        root.addProperty("ultrawarm", ultrawarm)
        return this
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param natural
     * @return this
     */
    fun natural(natural: Boolean): DimensionTypeBuilder {
        root.addProperty("natural", natural)
        return this
    }

    /**
     * Overworld -> 1.0D
     * Nether -> 8.0D
     * End -> 1.0D
     * @param coordinate_scale
     * @return this
     */
    fun coordinate_scale(coordinate_scale: Double): DimensionTypeBuilder {
        require(coordinate_scale >= 0.00001) { "Coordinate scale can't be higher than 0.00001D! Found $coordinate_scale" }
        require(coordinate_scale <= 30000000) { "Coordinate scale can't be higher than 30000000D! Found $coordinate_scale" }
        root.addProperty("coordinate_scale", coordinate_scale)
        return this
    }

    /**
     * Overworld -> 0.0
     * Nether -> 0.1
     * End -> 0.0
     * @param ambientLight
     * @return
     */
    fun ambientLight(ambientLight: Float): DimensionTypeBuilder {
//        if (ambientLight > 1.0F) throw new IllegalArgumentException("Ambient light can't be higher than 1.0F! Found " + ambientLight);
        root.addProperty("ambient_light", ambientLight)
        return this
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param hasSkylight
     * @return this
     */
    fun hasSkylight(hasSkylight: Boolean): DimensionTypeBuilder {
        root.addProperty("has_skylight", hasSkylight)
        return this
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param hasCeiling
     * @return
     */
    fun hasCeiling(hasCeiling: Boolean): DimensionTypeBuilder {
        root.addProperty("has_ceiling", hasCeiling)
        return this
    }

    /**
     * Overworld -> false
     * Nether -> false
     * End -> true
     * @param hasEnderDragonFight
     * @return this
     */
    fun hasEnderDragonFight(hasEnderDragonFight: Boolean): DimensionTypeBuilder {
        root.addProperty("has_ender_dragon_fight", hasEnderDragonFight)
        return this
    }

    /**
     * A block tag of which the blocks will not stop burning in the dimension.
     * Overworld -> minecraft:infiniburn_overworld
     * Nether -> minecraft:infiniburn_nether
     * End -> minecraft:infiniburn_end
     * @param infiniburn The block tag id.
     * @return this
     */
    fun infiniburn(infiniburn: Identifier): DimensionTypeBuilder {
        root.addProperty("infiniburn", infiniburn.toString())
        return this
    }

    /**
     * Overworld -> 384
     * Nether -> 128
     * End -> 256
     */
    fun minimumY(minimumY: Int): DimensionTypeBuilder {
        root.addProperty("min_y", minimumY)
        return this
    }

    /**
     * Overworld -> 384
     * Nether -> 128
     * End -> 256
     */
    fun height(height: Int): DimensionTypeBuilder {
        root.addProperty("height", height)
        return this
    }

    /**
     * Overworld -> 256
     * Nether -> 128
     * End -> 256
     * @param logicalHeight
     * @return
     */
    fun logicalHeight(logicalHeight: Int): DimensionTypeBuilder {
        root.addProperty("logical_height", logicalHeight)
        return this
    }

    /**
     * Set the fixed time of a dimension, do not set if you want a day-night cycle.
     * Nether -> 18000
     * End -> 6000
     * @param fixedTime Time of the days in ticks
     * @return this
     */
    fun fixedTime(fixedTime: Long): DimensionTypeBuilder {
        root.addProperty("fixed_time", fixedTime)
        return this
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> true
     * @param hasRaids
     * @return
     */
    fun hasRaids(hasRaids: Boolean): DimensionTypeBuilder {
        root.addProperty("has_raids", hasRaids)
        return this
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param respawnAnchorWork
     * @return
     */
    fun respawnAnchorWorks(respawnAnchorWork: Boolean): DimensionTypeBuilder {
        root.addProperty("respawn_anchor_works", respawnAnchorWork)
        return this
    }

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param bedWorks
     * @return this
     */
    fun bedWorks(bedWorks: Boolean): DimensionTypeBuilder {
        root.addProperty("bed_works", bedWorks)
        return this
    }

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param piglinSafe
     * @return this
     */
    fun piglinSafe(piglinSafe: Boolean): DimensionTypeBuilder {
        root.addProperty("piglin_safe", piglinSafe)
        return this
    }

    /**
     * Effects determine the sky effect of the dimension.
     *
     * Overworld -> minecraft:overworld
     * Nether -> minecraft:the_nether
     * End -> minecraft:the_end
     * @param effects thing
     * @return this
     */
    fun effects(effects: String): DimensionTypeBuilder {
        root.addProperty("effects", effects)
        return this
    }
}