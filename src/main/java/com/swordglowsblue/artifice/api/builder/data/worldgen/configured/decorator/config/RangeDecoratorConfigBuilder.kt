package com.swordglowsblue.artifice.api.builder.data.worldgen.configured.decorator.config

class RangeDecoratorConfigBuilder : DecoratorConfigBuilder() {
    fun bottomOffset(bottomOffset: Int): RangeDecoratorConfigBuilder {
        this.root.addProperty("bottom_offset", bottomOffset)
        return this
    }

    fun topOffset(topOffset: Int): RangeDecoratorConfigBuilder {
        this.root.addProperty("top_offset", topOffset)
        return this
    }

    fun maximum(maximum: Int): RangeDecoratorConfigBuilder {
        this.root.addProperty("maximum", maximum)
        return this
    }
}