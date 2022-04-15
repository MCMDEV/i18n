package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Optional;

public class EntityMetadataAdapter extends PacketAdapter implements ITranslationAdapter {
    public EntityMetadataAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.getPacket().getWatchableCollectionModifier().modify(0, watchableObjects -> {
            watchableObjects.forEach(wrappedWatchableObject -> {
                if (wrappedWatchableObject.getValue() instanceof Optional<?> optional) {
                    optional.ifPresent(o -> {
                        if(MinecraftReflection.getIChatBaseComponentClass().isAssignableFrom(o.getClass())) {
                            WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromHandle(o);
                            WrappedChatComponent translated = translate(event.getPlayer(), wrappedChatComponent);
                            wrappedWatchableObject.setValue(Optional.of(translated.getHandle()));
                        }
                    });
                }
                if(wrappedWatchableObject.getValue().getClass().equals(WrappedChatComponent.class))   {
                    wrappedWatchableObject.setValue(translate(event.getPlayer(), (WrappedChatComponent) wrappedWatchableObject.getValue()));
                }
            });
            return watchableObjects;
        });
    }
}
