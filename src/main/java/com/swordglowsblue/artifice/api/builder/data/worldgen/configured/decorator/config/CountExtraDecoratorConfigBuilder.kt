package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

class CountExtraDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun count(count: Int): CountExtraDecoratorConfigBuilder {
        this.root.addProperty("count", count)
        return this
    }

    fun extraCount(extraCount: Int): CountExtraDecoratorConfigBuilder {
        this.root.addProperty("extra_count", extraCount)
        return this
    }

    fun extraChance(extraChance: Float): CountExtraDecoratorConfigBuilder {
        this.root.addProperty("extra_chance", extraChance)
        return this
    }
}