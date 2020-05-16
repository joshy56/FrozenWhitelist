package me.joshy23.frozenwhitelist.util;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigurationHelper extends YamlConfiguration {
    @Getter
    private final String fileName;
    private final JavaPlugin plugin;
    private final File folder;

    public ConfigurationHelper(JavaPlugin plugin, String filename, String fileExtension, File folder) {
        this.folder = folder;
        this.plugin = plugin;
        this.fileName = filename + (filename.endsWith(fileExtension) ? "" : fileExtension);
        this.createFile();
    }

    public ConfigurationHelper(final JavaPlugin plugin, final String fileName) {
        this(plugin, fileName, ".yml");
    }

    public ConfigurationHelper(final JavaPlugin plugin, final String fileName, final String fileExtension) {
        this(plugin, fileName, fileExtension, plugin.getDataFolder());
    }

    @Override
    public String getString(String path) {
        String getted;
        try {
            getted = super.getString(path);
        } catch (NullPointerException e) {
            getted = path;
        }
        return getted.replace('&', '§');
    }

    public ItemStack getItemStack(String path, ItemStack def) {
        if (!this.contains(path)) return def;
        return super.getItemStack(path);
    }

    public <T> T get(Class<T> clazz, String path) {
        Object obj = super.get(path);
        return clazz.cast(obj);
    }

    private void createFile() {
        try {
            final File file = new File(folder, this.fileName);
            if (!file.exists()) {
                if (this.plugin.getResource(this.fileName) != null) {
                    this.plugin.saveResource(this.fileName, false);
                } else {
                    this.save(file);
                }
                this.load(file);
            } else {
                this.load(file);
                this.save(file);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Location getLocation(String path, Location def){
        if(!contains(path)) return def;
        return Location.deserialize(super.getConfigurationSection(path).getValues(true));
    }

    public Location getLocation(String path){
        if(!contains(path)) return null;
        return Location.deserialize(super.getConfigurationSection(path).getValues(true));
    }

    public void save() {
        final File folder = this.plugin.getDataFolder();
        try {
            this.save(new File(folder, this.fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

