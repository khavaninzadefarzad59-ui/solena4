package com.example.solenaassistant

import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

/**
 * SolenaVoiceInteractionSessionService
 *
 * Provides the session instance when the voice interaction is invoked.
 */
class SolenaVoiceInteractionSessionService : VoiceInteractionSessionService() {
    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        return SolenaVoiceInteractionSession(this)
    }
}