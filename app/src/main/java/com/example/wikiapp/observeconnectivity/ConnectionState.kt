package com.example.wikiapp.observeconnectivity

sealed class ConnectionState {
    object Available: ConnectionState()
    object UnAvailable: ConnectionState()
}