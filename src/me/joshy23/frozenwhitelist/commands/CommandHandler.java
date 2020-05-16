package me.joshy23.frozenwhitelist.commands;

import me.joshy23.frozenwhitelist.util.TextHelper;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements TabExecutor {
    private List<BaseCommand> commands = new ArrayList<>();
    private String main = "fwl";

    public CommandHandler() {
        commands.add(new PlayerAddCommand());
        commands.add(new PlayerRemoveCommand());
    }

    public BaseCommand getCommand(String name) {
        List<String> aliases;
        for (BaseCommand command : commands) {
            if (command.getName().equalsIgnoreCase(name)) return command;
            aliases = command.getAliases(new String[]{});
            if (aliases != null && !aliases.isEmpty()) {
                for (String alias : aliases) {
                    if (name.equalsIgnoreCase(alias)) return command;
                }
            }
        }
        return null;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    TextComponent component;
                    p.sendMessage(TextHelper.getColor("&7&m------&8(&3Frozen&fWhitelist&8&m)&7&m------"));
                    for (BaseCommand object : commands) {
                        component = new TextComponent();
                        component.setText(TextHelper.getColor("&e- &6/fwl " + object.getName()));
                        component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/fwl " + object.getName()));
                        p.spigot().sendMessage(component);
                    }
                    p.sendMessage(TextHelper.getColor("&7&m------&8(&3Frozen&fWhitelist&8&m)&7&m------"));
                } else {
                    sender.sendMessage(TextHelper.getColor("&7&m------&8(&3Frozen&fWhitelist&8&m)&7&m------"));
                    for (BaseCommand object : commands) {
                        sender.sendMessage(TextHelper.getColor("&e- &6/fwl " + object.getName()));
                    }
                    sender.sendMessage(TextHelper.getColor("&7&m------&8(&3Frozen&fWhitelist&8&m)&7&m------"));
                }
                return true;
            }
            BaseCommand target = getCommand(args[0]);
            if (target == null) {
                sender.sendMessage(TextHelper.getColor("&cInvalid sub command."));
                return true;
            }
            try {
                target.onCommand(sender, args);
            } catch (Exception e) {
                sender.sendMessage(TextHelper.getColor("&cAn exception has been occurred."));
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> aliases = new ArrayList<>();
        List<String> aliases1;
        for (BaseCommand baseCommand : commands) {
            if (args.length == 1) {
                aliases.add(baseCommand.getName());
            }
            if (args.length >= 2) {
                aliases1 = baseCommand.getAliases(args);
                if (aliases1 != null && !aliases1.isEmpty()) {
                    aliases.addAll(baseCommand.getAliases(args));
                }
            }
        }
        return aliases;
    }
}
