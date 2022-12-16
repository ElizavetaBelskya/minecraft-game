package ru.kpfu.itis.belskaya.helpers;

import ru.kpfu.itis.belskaya.protocol.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class PlayerListDiffUtils {

    private static boolean isBigger(List<PlayerEntity> oldPlayers, List<PlayerEntity> newPlayers) {
        if (oldPlayers.size() > newPlayers.size()) {
            return false;
        } else {
            return true;
        }
    }

    public static List<PlayerEntity> changedPlayers(List<PlayerEntity> oldPlayers, List<PlayerEntity> newPlayers) {
        if (isBigger(oldPlayers, newPlayers)) {
            List<PlayerEntity> changedPlayers = new ArrayList<PlayerEntity>();
            for (PlayerEntity oldPlayer : oldPlayers) {
                for (PlayerEntity newPlayer : newPlayers) {
                    if (oldPlayer != newPlayer) {
                        changedPlayers.add(newPlayer);
                    }
                }
            }
            return changedPlayers;
        } else {
            return null;
        }
    }


    public static List<PlayerEntity> removedPlayers(List<PlayerEntity> oldPlayers, List<PlayerEntity> newPlayers) {
        if (!isBigger(oldPlayers, newPlayers)) {
            List<PlayerEntity> changedPlayers = new ArrayList<>();
            for (PlayerEntity oldPlayer : oldPlayers) {
                for (PlayerEntity newPlayer : newPlayers) {
                    if (oldPlayer != newPlayer) {
                        changedPlayers.add(newPlayer);
                    }
                }
            }
            return changedPlayers;
        } else {
            return null;
        }
    }


}
