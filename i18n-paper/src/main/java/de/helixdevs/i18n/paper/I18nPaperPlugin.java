package de.helixdevs.i18n.paper;

import com.comphenix.protocol.ProtocolLibrary;
import de.helixdevs.i18n.adventure.AdventureTranslationConsumer;
import de.helixdevs.i18n.api.TranslationConsumer;
import de.helixdevs.i18n.common.command.api.Command;
import de.helixdevs.i18n.common.platform.I18nPlatform;
import de.helixdevs.i18n.common.platform.I18nPlugin;
import de.helixdevs.i18n.paper.command.BukkitSender;
import de.helixdevs.i18n.paper.protocollib.*;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class I18nPaperPlugin extends JavaPlugin implements I18nPlugin {

    private I18nPlatform platform;

    @Override
    public void onEnable() {
        this.platform = new I18nPlatform(this);
        platform.enable();

        ProtocolLibrary.getProtocolManager().addPacketListener(new BossbarAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new ChatAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new DisconnectAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new EntityMetadataAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new EquipmentAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new HeaderFooterAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new ObjectiveAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new OpenWindowAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new PlayerInfoAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new SetSlotAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new TeamAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new TitleAdapter(this));
        ProtocolLibrary.getProtocolManager().addPacketListener(new WindowItemsAdapter(this));
    }

    @Override
    public void onDisable() {
        platform.disable();
    }

    @Override
    public String getConfigString(String key) {
        return getConfig().getString(key);
    }

    @Override
    public List<String> getConfigStringList(String key) {
        return getConfig().getStringList(key);
    }

    @Override
    public Set<TranslationConsumer> getConsumers() {
        Set<TranslationConsumer> consumers = new HashSet<>();
        consumers.add(new AdventureTranslationConsumer());
        return consumers;
    }

    @Override
    public void registerCommand(Command command) {
        PluginCommand pluginCommand = getCommand(command.getLabel());
        if(pluginCommand == null) return;
        pluginCommand.setPermission(command.getPermission());
        pluginCommand.setExecutor((sender, bukkitCommand, label, args) -> {
            command.execute(new BukkitSender(sender), args);
            return true;
        });
    }
}
