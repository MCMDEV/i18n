package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class SetSlotAdapter extends PacketAdapter implements ITranslationAdapter {
    public SetSlotAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.SET_SLOT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        handleItem(event.getPlayer(), event.getPacket(), 0);
    }
}
