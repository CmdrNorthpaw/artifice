@file:JvmName("Artifice")

package com.swordglowsblue.artifice

import com.swordglowsblue.artifice.impl.ArtificeResourcePack
import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ClientResourcePackBuilder
import com.swordglowsblue.artifice.impl.ArtificeResourcePack.ServerResourcePackBuilder
import com.swordglowsblue.artifice.api.util.Builder
import com.swordglowsblue.artifice.common.ArtificeRegistry
import com.swordglowsblue.artifice.impl.ArtificeImpl
import com.swordglowsblue.artifice.impl.DynamicResourcePackFactory
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.resource.ResourceType
import net.minecraft.util.Identifier

/**
 * Register a new server-side resource pack, creating resources with the given lambda.
 *
 * @param id The pack ID.
 * @param register A lambda which will be passed a [ServerResourcePackBuilder].
 * @return The registered pack.
 */

fun registerDataPack(id: Identifier, register: Builder<ServerResourcePackBuilder>) {
    ArtificeImpl.registerSafely(
        ArtificeRegistry.DATA,
        id,
        DynamicResourcePackFactory(ResourceType.SERVER_DATA, id, register)
    )
}

/**
 * Register a new client-side resource pack.
 *
 * @param id   The pack ID.
 * @param pack The pack to register.
 * @return The registered pack.
 */
@Environment(EnvType.CLIENT)
fun registerAssets(id: String, pack: ArtificeResourcePack): ArtificeResourcePack {
    return registerAssets(Identifier(id), pack)
}

/**
 * Register a new server-side resource pack.
 *
 * @param id   The pack ID.
 * @param pack The pack to register.
 * @return The registered pack.
 */
fun registerDataPack(id: String, pack: ArtificeResourcePack): ArtificeResourcePack {
    val identifier = requireNotNull(Identifier.tryParse(id)) { "Could not parse $id to an identifier!" }
    return registerDataPack(identifier, pack)
}

fun registerDataPack(id: Identifier, pack: ArtificeResourcePack): ArtificeResourcePack {
    require(pack.type == ResourceType.SERVER_DATA) { "Cannot register a resource-pack as a data pack" }
    return ArtificeImpl.registerSafely(ArtificeRegistry.DATA, id, pack)
}

/**
 * Register a new client-side resource pack.
 *
 * @param id   The pack ID.
 * @param pack The pack to register.
 * @return The registered pack.
 * @see ArtificeResourcePack.ofAssets
 */

@Environment(EnvType.CLIENT)
fun registerAssets(id: Identifier, pack: ArtificeResourcePack): ArtificeResourcePack {
    require(pack.type == ResourceType.CLIENT_RESOURCES) { "Cannot register a server-side pack as assets" }
    return ArtificeImpl.registerSafely(ArtificeRegistry.ASSETS, id, pack)
}

/**
 * Register a new client-side resource pack, creating resources with the given lambda.
 * @param id The pack ID.
 * @param register A lambda which will be passed a [ClientResourcePackBuilder].
 * @return The registered pack.
 */
@Environment(EnvType.CLIENT)
fun registerAssetPack(id: String, register: Builder<ClientResourcePackBuilder>) {
    val identifier = requireNotNull(Identifier.tryParse(id)) { "Could not parse $id to an identifier!" }
    registerAssetPack(identifier, register)
}

/**
 * Register a new server-side resource pack, creating resources with the given lambda.
 *
 * @param id       The pack ID.
 * @param register A lambda which will be passed a [ServerResourcePackBuilder].
 * @return The registered pack.
 */
fun registerDataPack(id: String, register: Builder<ServerResourcePackBuilder>) {
    val identifier = requireNotNull(Identifier.tryParse(id)) { "Could not parse $id to an identifier!" }
    registerDataPack(identifier, register)
}

/**
 * Register a new client-side resource pack, creating resources with the given lambda.
 *
 * @param id The pack ID.
 * @param register A lambda which will be passed a [ClientResourcePackBuilder].
 * @return The registered pack.
 */
@Environment(EnvType.CLIENT)
fun registerAssetPack(id: Identifier, register: Builder<ClientResourcePackBuilder>) {
    ArtificeImpl.registerSafely(
        ArtificeRegistry.ASSETS,
        id,
        DynamicResourcePackFactory(ResourceType.CLIENT_RESOURCES, id, register)
    )
}


