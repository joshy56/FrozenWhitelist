package me.joshy23.frozenwhitelist.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface BaseCommand {

    void onCommand(CommandSender sender, String[] args);

    String getName();

    List<String> getAliases(String[] args);

}
