package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class HeaderFooterAdapter extends PacketAdapter implements ITranslationAdapter {
    public HeaderFooterAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        handleChatComponent(event.getPlayer(), event.getPacket(), 0);
        handleChatComponent(event.getPlayer(), event.getPacket(), 1);
    }
}
