package com.swordglowsblue.artifice.api.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/** Utilities for modifying [Identifier]s.  */
object IdUtils {
    /**
     * Add a suffix to the path of the given [Identifier].
     * @param id The base ID.
     * @param suffix The suffix to add.
     * @return A new [Identifier] with the suffixed path.
     */
    fun wrapPath(id: Identifier, suffix: String): Identifier {
        return wrapPath("", id, suffix)
    }
    /**
     * Add a prefix and suffix to the path of the given [Identifier].
     * @param prefix The prefix to add.
     * @param id The base ID.
     * @param suffix The suffix to add.
     * @return A new [Identifier] with the wrapped path.
     */
    @JvmStatic
    @JvmOverloads
    fun wrapPath(prefix: String, id: Identifier, suffix: String = ""): Identifier {
        return if (prefix.isEmpty() && suffix.isEmpty()) id else Identifier(
            id.namespace,
            prefix + id.path + suffix
        )
    }

    /**
     * If the given [Identifier] has the namespace "minecraft" (the default namespace),
     * return a copy with the given `defaultNamespace`. Otherwise, return the ID unchanged.
     * @param id The base ID.
     * @param defaultNamespace The namespace to replace `minecraft` with if applicable.
     * @return The given ID with its namespace replaced if applicable.
     */
    fun withDefaultNamespace(id: Identifier, defaultNamespace: String): Identifier {
        return if (id.namespace == "minecraft") Identifier(defaultNamespace, id.path) else id
    }

    val Item.id: Identifier
    get()  = Registry.ITEM.getId(this)

    val Identifier.asItem: Item?
    get() = Registry.ITEM.getOrEmpty(this).orElse(null)

    fun parseId(toParse: String?): Identifier? = Identifier.tryParse(toParse)

    val JsonElement.asId: Identifier?
    get() = Identifier.tryParse(this.asString)
}