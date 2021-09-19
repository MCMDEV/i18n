package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class BossbarAdapter extends PacketAdapter implements ITranslationAdapter {
    public BossbarAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.BOSS);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        handleChatComponent(event.getPlayer(), event.getPacket(), 0);
    }
}
