package com.swordglowsblue.artifice.api.virtualpack

import net.fabricmc.api.EnvType
import net.minecraft.resource.ResourcePackProfile
import com.swordglowsblue.artifice.impl.ArtificeResourcePackImpl
import com.swordglowsblue.artifice.impl.ArtificeResourcePack
import net.fabricmc.api.Environment
import java.util.function.Supplier

/**
 * A wrapper around [ResourcePackProfile] exposing optionality/visibility.
 *
 * @see ArtificeResourcePack.ClientResourcePackBuilder.setOptional
 *
 * @see ArtificeResourcePack.ClientResourcePackBuilder.setVisible
 */
@Environment(EnvType.CLIENT)
class ArtificeResourcePackContainer(
    /**
     * @return Whether this pack is optional.
     */
    val isOptional: Boolean,
    /**
     * @return Whether this pack is visible.
     */
    val isVisible: Boolean, wrapping: ResourcePackProfile
) : ResourcePackProfile(
    wrapping.name,
    !isOptional, Supplier { wrapping.createResourcePack() },
    wrapping.displayName,
    wrapping.description,
    wrapping.compatibility,
    wrapping.initialPosition,
    wrapping.isPinned,
    ArtificeResourcePackImpl.ARTIFICE_RESOURCE_PACK_SOURCE
)