package me.joshy23.frozenwhitelist;

import me.joshy23.frozenwhitelist.commands.CommandHandler;
import me.joshy23.frozenwhitelist.listener.PlayerListener;
import me.joshy23.frozenwhitelist.util.ConfigurationHelper;
import me.joshy23.frozenwhitelist.util.TextHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FrozenWhitelist extends JavaPlugin {
    private ConfigurationHelper messages;
    private ConfigurationHelper data;
    private HashMap<UUID, Boolean> online = new HashMap<>();
    private CommandHandler commandHandler;

    public void onEnable() {
        messages = new ConfigurationHelper(this, "messages");
        data = new ConfigurationHelper(this, "data");
        loadData();
        commandHandler = new CommandHandler();
        setCommands();
        setEvents();
        Bukkit.getConsoleSender().sendMessage(TextHelper.getColor("%prefix% &aEnabled FrozenWhitelist"));
    }

    public void onDisable() {
        saveData();
        Bukkit.getConsoleSender().sendMessage(TextHelper.getColor("%prefix% &aDisabled FrozenWhitelist"));
    }

    private void saveData() {
        for (Map.Entry<UUID, Boolean> entry : online.entrySet()) {
            data.set("Players."+entry.getKey().toString(), entry.getValue());
        }
        data.save();
    }

    private void loadData() {
        if(data.contains("Players")) {
            for (String key : data.getConfigurationSection("Players").getKeys(false)) {
                online.put(UUID.fromString(key), data.getBoolean(key));
            }
        }
    }

    public ConfigurationHelper getMessages() {
        return messages;
    }

    public ConfigurationHelper getData() {
        return data;
    }

    public HashMap<UUID, Boolean> getOnline() {
        return online;
    }

    private void setCommands() {
        this.getCommand("fwl").setExecutor(commandHandler);
        this.getCommand("fwl").setTabCompleter(commandHandler);
    }

    private void setEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

}
