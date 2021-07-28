package com.swordglowsblue.artifice.common

import net.minecraft.resource.ResourcePackProfile

interface ServerResourcePackProfileLike {
    fun <T : ResourcePackProfile> toServerResourcePackProfile(factory: ResourcePackProfile.Factory): ResourcePackProfile
}