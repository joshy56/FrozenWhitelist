package me.joshy23.frozenwhitelist.util;

import java.util.ArrayList;
import java.util.List;

public class TextHelper {

    public static String getColor(String message) {
        return message.replaceAll("%prefix%","&7(&3Frozen&fWhitelist&7)&r").replace('&', '§');
    }

    public List<String> getColorList(List<String> messages) {
        List<String> result = new ArrayList<>();
        for (String message : messages) {
            result.add(getColor(message));
        }
        return result;
    }
}
