package com.swordglowsblue.artifice.common

import com.mojang.serialization.Lifecycle
import net.fabricmc.api.EnvType
import net.minecraft.util.registry.MutableRegistry
import com.swordglowsblue.artifice.common.ClientResourcePackProfileLike
import net.minecraft.util.registry.SimpleRegistry
import com.swordglowsblue.artifice.common.ServerResourcePackProfileLike
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

object ArtificeRegistry {
    /**
     * The [Registry] for client-side resource packs.
     */
    @JvmField
    @Environment(EnvType.CLIENT)
    val ASSETS: MutableRegistry<ClientResourcePackProfileLike> = SimpleRegistry(
        RegistryKey.ofRegistry(Identifier("artifice", "common_assets")),
        Lifecycle.stable()
    )

    /**
     * The [Registry] for server-side resource packs.
     */
    @JvmField
    val DATA: MutableRegistry<ServerResourcePackProfileLike> =
        Registry.register<Any, SimpleRegistry<ServerResourcePackProfileLike>>(
            Registry.REGISTRIES as Registry<Any>,
            Identifier("artifice", "common_data_packs"),
            SimpleRegistry(
                RegistryKey.ofRegistry(Identifier("artifice", "data")),
                Lifecycle.stable()
            )
        )
}