package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class EquipmentAdapter extends PacketAdapter implements ITranslationAdapter {
    public EquipmentAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_EQUIPMENT);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if(event.getPacket().getItemModifier().size() == 0) return;
        handleItem(event.getPlayer(), event.getPacket(), 0);
    }
}
