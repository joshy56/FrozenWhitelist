package me.joshy23.frozenwhitelist.listener;

import me.joshy23.frozenwhitelist.FrozenWhitelist;
import me.joshy23.frozenwhitelist.util.ConfigurationHelper;
import me.joshy23.frozenwhitelist.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerListener implements Listener {
    private FrozenWhitelist plugin = FrozenWhitelist.getPlugin(FrozenWhitelist.class);
    private ConfigurationHelper messages;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e){
        messages = plugin.getMessages();
        if(!plugin.getOnline().containsKey(e.getUniqueId())){
            plugin.getOnline().put(e.getUniqueId(),false);
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, TextHelper.getColor(messages.getString("error.join.kick-message","%prefix% &cYou aren't whitelisted.")));
            return;
        }
        if(!plugin.getOnline().get(e.getUniqueId())){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, TextHelper.getColor(messages.getString("error.join.kick-message","%prefix% &cYou aren't whitelisted.")));
            return;
        }
        for(Player p: Bukkit.getOnlinePlayers()){
            p.sendMessage(TextHelper.getColor(messages.getString("success.join","%prefix% &a%player% has been joined.").replaceAll("%player%",Bukkit.getPlayer(e.getUniqueId()).getName())));
        }
    }

}
