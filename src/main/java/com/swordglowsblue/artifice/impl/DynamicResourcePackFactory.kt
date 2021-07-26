package com.swordglowsblue.artifice.impl

import com.swordglowsblue.artifice.common.ClientOnly
import com.swordglowsblue.artifice.common.ClientResourcePackProfileLike
import com.swordglowsblue.artifice.common.ServerResourcePackProfileLike
import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ResourcePackBuilder
import net.minecraft.resource.ResourcePackProfile
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier
import java.util.function.Consumer

class DynamicResourcePackFactory<T : ResourcePackBuilder>(
    private val type: ResourceType,
    private val identifier: Identifier,
    private val init: (T) -> Unit
) : ClientResourcePackProfileLike, ServerResourcePackProfileLike {
    override fun <T: ResourcePackProfile> toClientResourcePackProfile(factory: ResourcePackProfile.Factory)
    : ClientOnly<ResourcePackProfile> {
        return ArtificeResourcePackImpl(type, identifier, init).toClientResourcePackProfile<T>(factory)
    }

    override fun <T: ResourcePackProfile> toServerResourcePackProfile(factory: ResourcePackProfile.Factory): ResourcePackProfile {
        return ArtificeResourcePackImpl(type, identifier, init).toServerResourcePackProfile<T>(factory)
    }
}