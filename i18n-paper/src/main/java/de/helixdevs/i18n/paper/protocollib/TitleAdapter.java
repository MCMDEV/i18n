package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class TitleAdapter extends PacketAdapter implements ITranslationAdapter {
    public TitleAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.SET_TITLE_TEXT, PacketType.Play.Server.SET_SUBTITLE_TEXT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        handleChatComponent(event.getPlayer(), event.getPacket(), 0);
    }
}
