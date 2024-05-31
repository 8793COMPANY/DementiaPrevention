package com.corporation8793.dementia.game.pulse_countdown

import com.corporation8793.dementia.game.pulse_countdown.PulseCountDown

/**
 * Start countdown and invoke callback when it's ended
 */
fun PulseCountDown.start(callback: () -> Unit = {}) = start(OnCountdownCompleted { callback() })