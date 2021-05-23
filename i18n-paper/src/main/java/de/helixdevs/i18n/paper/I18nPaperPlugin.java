package de.helixdevs.i18n.paper;

import com.comphenix.protocol.ProtocolLibrary;
import de.helixdevs.i18n.adventure.AdventureTranslationConsumer;
import de.helixdevs.i18n.api.TranslationConsumer;
import de.helixdevs.i18n.common.platform.I18nPlatform;
import de.helixdevs.i18n.common.platform.I18nPlugin;
import de.helixdevs.i18n.paper.protocollib.TranslationPacketAdapter;
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

        ProtocolLibrary.getProtocolManager().addPacketListener(new TranslationPacketAdapter(this));
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
}
