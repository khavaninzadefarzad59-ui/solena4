package com.example.solenaassistant

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log

/**
 * SolenaVoiceInteractionSession
 *
 * Minimal VoiceInteractionSession required to fulfill the VoiceInteractionService contract.
 * Purely for technical testing without any AI, wake-word, or network logic.
 */
class SolenaVoiceInteractionSession(context: Context) : VoiceInteractionSession(context) {

    companion object {
        private const val TAG = "SolenaVoiceSession"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "SolenaVoiceInteractionSession created.")
    }

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
        Log.d(TAG, "SolenaVoiceInteractionSession shown with flags: $showFlags")
    }

    override fun onHide() {
        super.onHide()
        Log.d(TAG, "SolenaVoiceInteractionSession hidden.")
    }
}