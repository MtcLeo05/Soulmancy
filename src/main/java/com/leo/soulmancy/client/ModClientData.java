package com.leo.soulmancy.client;

import com.leo.soulmancy.data.PlayerData;
import com.leo.soulmancy.data.SoulData;

public class ModClientData {

    private static PlayerData playerData = new PlayerData();
    private static SoulData currentChunkData = new SoulData(0, 0);

    public static void setPlayerData(PlayerData playerData) {
        ModClientData.playerData = playerData;
    }

    public static PlayerData getPlayerData() {
        return playerData;
    }

    public static void setCurrentChunkData(SoulData currentChunkData) {
        ModClientData.currentChunkData = currentChunkData;
    }

    public static SoulData getCurrentChunkData() {
        return currentChunkData;
    }
}
