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

public class PlayerAddCommand implements BaseCommand{
    private FrozenWhitelist plugin = FrozenWhitelist.getPlugin(FrozenWhitelist.class);
    private ConfigurationHelper messages;

    public void onCommand(CommandSender sender, String[] args) {
        messages = plugin.getMessages();
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("fwl.add")){
                if(args.length == 2){
                    plugin.getOnline().put(Bukkit.getOfflinePlayer(args[1]).getUniqueId(),true);
                    p.sendMessage(TextHelper.getColor(messages.getString("success.add","%prefix% &aSuccessfully added to the whitelist")));
                }else{
                    p.sendMessage(TextHelper.getColor(messages.getString("error.add.invalid-name","%prefix% &cYou must put an player name.")));
                }
            }else{
                p.sendMessage(TextHelper.getColor(messages.getString("error.add.dont-has-permissions","%prefix% &cYou don't have permission to do that. &7(&e&mfwl.add&r&7)")));
            }
        }else{
            if(args.length == 2){
                plugin.getOnline().put(Bukkit.getOfflinePlayer(args[1]).getUniqueId(),true);
                sender.sendMessage(TextHelper.getColor(messages.getString("success.add","%prefix% &aSuccess added to the whitelist")));
            }else{
                sender.sendMessage(TextHelper.getColor(messages.getString("error.add.invalid-name","%prefix% &cYou must put an player name.")));
            }
        }
    }

    public String getName() {
        return "add";
    }

    public List<String> getAliases(String[] args) {
        List<String> aliases = new ArrayList<>();
        for (OfflinePlayer player: Bukkit.getOfflinePlayers()){
            aliases.add(player.getName());
        }
        return aliases;
    }
}
