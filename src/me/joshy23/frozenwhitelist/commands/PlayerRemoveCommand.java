package me.joshy23.frozenwhitelist.commands;

import me.joshy23.frozenwhitelist.FrozenWhitelist;
import me.joshy23.frozenwhitelist.util.ConfigurationHelper;
import me.joshy23.frozenwhitelist.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerRemoveCommand implements BaseCommand {
    private FrozenWhitelist plugin = FrozenWhitelist.getPlugin(FrozenWhitelist.class);
    private ConfigurationHelper messages;

    public void onCommand(CommandSender sender, String[] args) {
        messages = plugin.getMessages();
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("fwl.remove")){
                if(args.length == 2){
                    UUID uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                    if (plugin.getOnline().containsKey(uuid)){
                        plugin.getOnline().replace(uuid,false);
                        p.sendMessage(TextHelper.getColor(messages.getString("success.remove","%prefix% &aSuccessfully removed player from the whitelist.")));
                        OfflinePlayer offlineP = Bukkit.getOfflinePlayer(uuid);
                        if(offlineP.isOnline()){
                            offlineP.getPlayer().kickPlayer(TextHelper.getColor("%prefix% &cYou are removed from the whitelist."));
                        }
                    }
                }else{
                    p.sendMessage(TextHelper.getColor(messages.getString("error.remove.invalid-name","%prefix% &cYou must put an player name.")));
                }
            }else{
                p.sendMessage(TextHelper.getColor(messages.getString("error.remove.dont-has-permissions","%prefix% &cYou don't have permission to do that. &7(&e&mfwl.remove&r&7)")));
            }
        }else{
            if(args.length == 2){
                UUID uuid = UUID.nameUUIDFromBytes(args[1].getBytes());
                if (plugin.getOnline().containsKey(uuid)){
                    plugin.getOnline().replace(uuid,false);
                    sender.sendMessage(TextHelper.getColor(messages.getString("success.remove","%prefix% &aSuccessfully removed player from the whitelist.")));
                }
            }else{
                sender.sendMessage(TextHelper.getColor(messages.getString("error.remove.invalid-name","%prefix% &cYou must put an player name.")));
            }
        }
    }

    public String getName() {
        return "remove";
    }

    public List<String> getAliases(String[] args) {
        List<String> aliases = new ArrayList<>();
        if (args.length == 1) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                aliases.add(player.getName());
            }
        }
        return aliases;
    }
}
