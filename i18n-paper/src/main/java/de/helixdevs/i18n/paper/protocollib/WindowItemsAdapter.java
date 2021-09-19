package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class WindowItemsAdapter extends PacketAdapter implements ITranslationAdapter {
    public WindowItemsAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.WINDOW_ITEMS);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        handleItemList(event.getPlayer(), event.getPacket(), 0);
    }
}
