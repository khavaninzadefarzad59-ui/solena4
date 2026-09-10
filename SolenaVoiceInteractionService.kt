package com.example.solenaassistant

import android.service.voice.VoiceInteractionService
import android.util.Log

/**
 * SolenaVoiceInteractionService
 *
 * Minimal VoiceInteractionService for testing the Assistant Role in Android 10 (API 29).
 * No Wake Word, AI, chatbot, or network logic included as per technical test requirements.
 */
class SolenaVoiceInteractionService : VoiceInteractionService() {

    companion object {
        private const val TAG = "SolenaVoiceService"
    }

    override fun onReady() {
        super.onReady()
        Log.d(TAG, "SolenaVoiceInteractionService is ready and bound by Android system.")
    }

    override fun onShutdown() {
        super.onShutdown()
        Log.d(TAG, "SolenaVoiceInteractionService is shutting down.")
    }
}