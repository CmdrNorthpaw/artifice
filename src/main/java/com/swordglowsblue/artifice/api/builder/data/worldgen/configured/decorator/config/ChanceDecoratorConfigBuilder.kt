package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

class ChanceDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun chance(chance: Int): ChanceDecoratorConfigBuilder {
        this.root.addProperty("chance", chance)
        return this
    }
}