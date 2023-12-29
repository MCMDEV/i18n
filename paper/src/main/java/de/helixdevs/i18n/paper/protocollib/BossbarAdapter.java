package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.Plugin;

public class BossbarAdapter extends PacketAdapter implements ITranslationAdapter {

    public BossbarAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.BOSS);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        packet.getStructures().modify(1, internalStructure -> {
            WrappedChatComponent wrappedChatComponent = internalStructure.getChatComponents().readSafely(0);
            if(wrappedChatComponent == null) return internalStructure;
            handleChatComponent(event.getPlayer(), internalStructure, 0);
            return internalStructure;
        });
    }
}
