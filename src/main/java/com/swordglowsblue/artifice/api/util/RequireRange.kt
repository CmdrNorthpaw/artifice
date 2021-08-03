package com.swordglowsblue.artifice.api.util

internal fun <T: Number> requireRange(value: T?, min: T, max: T, name: String = "value"): T {
    requireNotNull(value)
    require(value >= min) { "$name can't be less than $min, found $value" }
    require(value <= max) { "$name can't be more than $max, found $value" }
    return value
}

operator fun Number.compareTo(value: Number): Int {
    val double = this.toDouble()
    val otherDouble = value.toDouble()

    return when {
        double > otherDouble -> 1
        double < otherDouble -> -1
        else -> 0
    }
}