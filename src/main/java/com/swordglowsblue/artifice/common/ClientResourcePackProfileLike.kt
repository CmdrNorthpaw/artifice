package com.swordglowsblue.artifice.common

import net.minecraft.resource.ResourcePackProfile
import com.swordglowsblue.artifice.common.ClientOnly

interface ClientResourcePackProfileLike {
    // Supplier to avoid loading ClientResourcePackProfile on the server
    fun <T : ResourcePackProfile> toClientResourcePackProfile(factory: ResourcePackProfile.Factory): ClientOnly<ResourcePackProfile>
}