package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.sun.org.apache.xpath.internal.operations.Bool
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.resource.JsonResource
import net.minecraft.util.Identifier

class DimensionTypeBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }) {
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

    var ultraWarm: Boolean
    get() = root["ultrawarm"].asBoolean
    set(value) = root.addProperty("ultrawarm", value)

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

    var isNatural: Boolean
    get() = root["isNatural"].asBoolean
    set(value) = root.addProperty("natural", value)

    /**
     * Overworld -> 1.0D
     * Nether -> 8.0D
     * End -> 1.0D
     * @param coordinate_scale
     * @return this
     */
    @Deprecated("", replaceWith = ReplaceWith("coordinateScale"))
    fun coordinate_scale(scale: Double): DimensionTypeBuilder {
        return coordinateScale(scale)
    }

    fun coordinateScale(scale: Double): DimensionTypeBuilder {
        require(scale >= 0.00001) { "Coordinate scale can't be higher than 0.00001D! Found $scale" }
        require(scale <= 30000000) { "Coordinate scale can't be higher than 30000000D! Found $scale" }
        root.addProperty("coordinate_scale", scale)
        return this
    }

    var coordinateScale: Double
    get() = root["coordinate_scale"].asDouble
    set(value) {
        require(value >= 0.00001) { "Coordinate scale can't be higher than 0.00001D! Found $value" }
        require(value <= 30000000) { "Coordinate scale can't be higher than 30000000D! Found $value" }
        root.addProperty("coordinate_scale", value)
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

    var ambientLight: Float
    get() = root["ambientLight"].asFloat
    set(value) = root.addProperty("ambientLight", value)

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

    var hasSkylight: Boolean
    get() = root["has_skylight"].asBoolean
    set(value) = root.addProperty("has_skylight", value)

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

    var hasCeiling: Boolean
    get() = root["has_ceiling"].asBoolean
    set(value) = root.addProperty("has_ceiling", value)

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

    var hasEnderDragonFight: Boolean
    get() = root["has_ender_dragon_fight"].asBoolean
    set(value) = root.addProperty("has_ender_dragon_fight", value)

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

    var infiniburnTag: Identifier
    get() = Identifier.tryParse(root["infiniburn"].asString)!!
    set(value) = root.addProperty("infiniburn", value.toString())

    /**
     * Overworld -> 384
     * Nether -> 128
     * End -> 256
     */
    fun minimumY(minimumY: Int): DimensionTypeBuilder {
        root.addProperty("min_y", minimumY)
        return this
    }

    var minimumY: Int
    get() = root["min_y"].asInt
    set(value) = root.addProperty("min_y", value)

    /**
     * Overworld -> 384
     * Nether -> 128
     * End -> 256
     */
    fun height(height: Int): DimensionTypeBuilder {
        root.addProperty("height", height)
        return this
    }

    var height: Int
    get() = root["height"].asInt
    set(value) = root.addProperty("height", value)

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

    var logicalHeight: Int
    get() = root["logical_height"].asInt
    set(value) = root.addProperty("logical_height", value)

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

    var fixedTime: Long
    get() = root["fixed_time"].asLong
    set(value) = root.addProperty("fixed_time", value)

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

    var hasRaids: Boolean
    get() = root["has_raids"].asBoolean
    set(value) = root.addProperty("has_raids", value)

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

    var respawnAnchorWorks: Boolean
    get() = root["respawn_anchor_works"].asBoolean
    set(value) = root.addProperty("respawn_anchor_works", value)

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

    var bedWorks: Boolean
    get() = root["bed_works"].asBoolean
    set(value) = root.addProperty("bed_works", value)

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

    var isPiglinSafe: Boolean
    get() = root["piglin_safe"].asBoolean
    set(value) = root.addProperty("piglin_safe", value)

    /**
     * Effects determine the sky effect of the dimension.
     *
     * Overworld -> minecraft:overworld
     * Nether -> minecraft:the_nether
     * End -> minecraft:the_end
     * @param effects thing
     * @return this
     */
    fun effects(effects: Identifier): DimensionTypeBuilder { this.effects = effects; return this }

    fun effects(effects: String): DimensionTypeBuilder {
        root.addProperty("effects", effects)
        return this
    }

    var effects: Identifier
    get() = Identifier.tryParse(root["effects"].asString)!!
    set(value) = root.addProperty("effects", value.toString())
}