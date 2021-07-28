package com.swordglowsblue.artifice.common

import net.fabricmc.api.EnvType
import net.minecraft.util.registry.MutableRegistry
import com.swordglowsblue.artifice.common.ClientResourcePackProfileLike
import net.minecraft.util.registry.SimpleRegistry
import com.swordglowsblue.artifice.common.ServerResourcePackProfileLike

/**
 * Wrapper around some EnvType.CLIENT object, to avoid directly referencing it in runtime
 * (the generic type parameter is removed at runtime, avoiding referencing the client-only class)
 * @param <T> Some class marked with @Environment(EnvType.CLIENT)
</T> */
class ClientOnly<T>(private val clientOnly: T) {
    fun get(): T {
        return clientOnly
    }
}