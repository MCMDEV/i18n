package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;

public class EntityMetadataAdapter extends PacketAdapter implements ITranslationAdapter {
    public EntityMetadataAdapter(Plugin plugin) {
        super(plugin, PacketType.Play.Server.ENTITY_METADATA);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        event.getPacket().getDataValueCollectionModifier().modify(0, wrappedDataValues -> {
            wrappedDataValues.forEach(wrappedDataValue -> {
                if (wrappedDataValue.getValue() instanceof Optional<?> optional) {
                    optional.ifPresent(o -> {
                        if(o instanceof WrappedChatComponent wrappedChatComponent)  {
                            WrappedChatComponent translated = translate(event.getPlayer(), wrappedChatComponent);
                            wrappedDataValue.setValue(Optional.of(translated.getHandle()));
                        }   else
                        if(MinecraftReflection.getIChatBaseComponentClass().isAssignableFrom(o.getClass())) {
                            WrappedChatComponent wrappedChatComponent = WrappedChatComponent.fromHandle(o);
                            WrappedChatComponent translated = translate(event.getPlayer(), wrappedChatComponent);
                            wrappedDataValue.setValue(Optional.of(translated.getHandle()));
                        }
                    });
                }
                if(wrappedDataValue.getValue().getClass().equals(WrappedChatComponent.class))   {
                    wrappedDataValue.setValue(translate(event.getPlayer(), (WrappedChatComponent) wrappedDataValue.getValue()));
                }
            });
            return wrappedDataValues;
        });
    }
}
