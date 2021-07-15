package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

class DepthAverageDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun count(count: Int): DepthAverageDecoratorConfigBuilder {
        this.root.addProperty("baseline", count)
        return this
    }

    fun spread(spread: Int): DepthAverageDecoratorConfigBuilder {
        this.root.addProperty("spread", spread)
        return this
    }
}