package com.swordglowsblue.artifice.impl

import com.swordglowsblue.artifice.impl.ArtificeImpl
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import org.apache.logging.log4j.LogManager

object ArtificeImpl {
    private val log4jLogger = LogManager.getLogger("Artifice")
    val LOGGER = log4jLogger
    fun <V, T : V> registerSafely(registry: Registry<V>, id: Identifier, entry: T): T {
        return Registry.register(registry, id, entry)
    }
}