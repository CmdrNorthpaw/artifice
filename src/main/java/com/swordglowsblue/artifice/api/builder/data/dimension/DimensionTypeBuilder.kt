package com.swordglowsblue.artifice.api.builder.data.dimension

import com.google.gson.JsonObject
import com.swordglowsblue.artifice.api.builder.TypedJsonBuilder
import com.swordglowsblue.artifice.api.dsl.ArtificeDsl
import com.swordglowsblue.artifice.api.dsl.Tag
import com.swordglowsblue.artifice.api.resource.JsonResource
import com.swordglowsblue.artifice.api.util.IdUtils.asId
import com.swordglowsblue.artifice.api.util.addProperty
import com.swordglowsblue.artifice.api.util.asTag
import net.minecraft.util.Identifier
import java.util.function.Function

@ArtificeDsl
class DimensionTypeBuilder : TypedJsonBuilder<JsonResource<JsonObject>>(
    JsonObject(),
    { root: JsonObject -> JsonResource(root) }
) {
    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param ultrawarm
     * @return this
     */
    fun ultrawarm(ultrawarm: Boolean) = apply { this.ultrawarm = ultrawarm }

    var ultrawarm: Boolean?
    get() = root["ultrawarm"].asBoolean
    set(value) = root.addProperty("ultrawarm", value)

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param natural
     * @return this
     */
    fun isNatural(natural: Boolean) = apply { isNatural = natural }

    var isNatural: Boolean?
    get() = root["natural"].asBoolean
    set(value) = root.addProperty("natural", value)

    /**
     * Overworld -> 1.0D
     * Nether -> 8.0D
     * End -> 1.0D
     * @param coordinate_scale
     * @return this
     */
    fun coordinateScale(scale: Double) = apply { coordinateScale = scale }

    var coordinateScale: Double?
    get() = root["coordinate_scale"].asDouble
    set(value) {
        requireNotNull(value)
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
    fun ambientLight(ambientLight: Float) = apply { this.ambientLight = ambientLight }

    var ambientLight: Float?
    get() = root["ambient_light"].asFloat
    set(value) = root.addProperty("ambient_light", value)

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param hasSkylight
     * @return this
     */
    fun hasSkylight(hasSkylight: Boolean) = apply { this.hasSkylight = hasSkylight }

    var hasSkylight: Boolean?
    get() = root["has_skylight"].asBoolean
    set(value) = root.addProperty("has_skylight", value)

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param hasCeiling
     * @return
     */
    fun hasCeiling(hasCeiling: Boolean) = apply { this.hasCeiling = hasCeiling }

    var hasCeiling
    get() = root["has_ceiling"].asBoolean
    set(value) = root.addProperty("has_ceiling", value)

    /**
     * Overworld -> false
     * Nether -> false
     * End -> true
     * @param hasEnderDragonFight
     * @return this
     */
    fun hasEnderDragonFight(hasEnderDragonFight: Boolean) = apply { this.hasEnderDragonFight = hasEnderDragonFight }

    var hasEnderDragonFight: Boolean?
    get() = root["has_ender_dragon_fight"]?.asBoolean
    set(value) = root.addProperty("has_ender_dragon_fight", value)

    /**
     * A block tag of which the blocks will not stop burning in the dimension.
     * Overworld -> minecraft:infiniburn_overworld
     * Nether -> minecraft:infiniburn_nether
     * End -> minecraft:infiniburn_end
     * @param infiniburn The block tag id.
     * @return this
     */
    fun infiniburn(infiniburn: Tag) = apply { this.infiniburn = infiniburn }

    var infiniburn: Tag?
    get() = root["infiniburn"].asTag
    set(value) = root.addProperty("infiniburn", value)

    /**
     * Overworld -> 384
     * Nether -> 128
     * End -> 256
     */
    fun minimumY(minimumY: Int) = apply { this.minimumY = minimumY }

    var minimumY: Int
    get() = root["min_y"].asInt
    set(value) = root.addProperty("min_y", value)

    /**
     * Overworld -> 384
     * Nether -> 128
     * End -> 256
     */
    fun height(height: Int) = apply { this.height = height }

    var height: Int?
    get() = root["height"].asInt
    set(value) = root.addProperty("height", value)

    /**
     * Overworld -> 256
     * Nether -> 128
     * End -> 256
     * @param logicalHeight
     * @return
     */
    fun logicalHeight(logicalHeight: Int) = apply { this.logicalHeight = logicalHeight }

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
    fun fixedTime(fixedTime: Long) = apply { this.fixedTime = fixedTime }

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
    fun hasRaids(hasRaids: Boolean) = apply { this.hasRaids = hasRaids }

    var hasRaids: Boolean?
    get() = root["has_raids"].asBoolean
    set(value) = root.addProperty("has_raids", value)

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param respawnAnchorWork
     * @return
     */
    fun respawnAnchorWorks(respawnAnchorWorks: Boolean) = apply { this.respawnAnchorWorks = respawnAnchorWorks }

    var respawnAnchorWorks: Boolean?
    get() = root["respawn_anchor_works"].asBoolean
    set(value) = root.addProperty("respawn_anchor_works", value)

    /**
     * Overworld -> true
     * Nether -> false
     * End -> false
     * @param bedWorks
     * @return this
     */
    fun bedWorks(bedWorks: Boolean) = apply { this.bedWorks = bedWorks }

    var bedWorks: Boolean?
    get() = root["bed_works"].asBoolean
    set(value) = root.addProperty("bed_works", value)

    /**
     * Overworld -> false
     * Nether -> true
     * End -> false
     * @param piglinSafe
     * @return this
     */
    fun isPiglinSafe(piglinSafe: Boolean) = apply { this.isPiglinSafe = piglinSafe }

    var isPiglinSafe: Boolean?
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
    fun effects(effects: String): DimensionTypeBuilder {
        root.addProperty("effects", effects)
        return this
    }

    var effects: Identifier?
    get() = root["effects"].asId
    set(value) = root.addProperty("effects", value.toString())
}