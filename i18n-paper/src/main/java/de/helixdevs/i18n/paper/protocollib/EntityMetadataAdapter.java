package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.Plugin;

public class EntityMetadataAdapter extends PacketAdapter implements ITranslationAdapter {
    public EntityMetadataAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.getPacket().getWatchableCollectionModifier().modify(0, watchableObjects -> {
            watchableObjects.forEach(wrappedWatchableObject -> {
                Class<?> handleType = wrappedWatchableObject.getHandleType();
                if(handleType.equals(WrappedChatComponent.class))   {
                    wrappedWatchableObject.setValue(translate(event.getPlayer(), (WrappedChatComponent) wrappedWatchableObject.getValue()));
                }
            });
            return watchableObjects;
        });
    }
}
