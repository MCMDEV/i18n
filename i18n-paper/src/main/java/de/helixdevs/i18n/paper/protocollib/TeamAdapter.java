package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

public class TeamAdapter extends PacketAdapter implements ITranslationAdapter {
    public TeamAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.SCOREBOARD_TEAM);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.getPacket().getOptionalStructures().modify(0, structureOptional -> {
            structureOptional.ifPresent(internalStructure -> {
                handleChatComponent(event.getPlayer(), internalStructure, 0);
                handleChatComponent(event.getPlayer(), internalStructure, 1);
                handleChatComponent(event.getPlayer(), internalStructure, 2);
            });
            return structureOptional;
        });
    }
}
