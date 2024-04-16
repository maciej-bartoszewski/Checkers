package org.example.checkers.game;

public enum Gamemode {
    ONE_PLAYER, TWO_PLAYERS, ONLINE;

    public boolean isOnePlayer() {
        return this == ONE_PLAYER;
    }

    public boolean isTwoPlayers() {
        return this == TWO_PLAYERS;
    }

    public boolean isOnline() {
        return this == ONLINE;
    }
}
