package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class TranslationPacketAdapter extends PacketAdapter {

    private static GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.gson();

    public TranslationPacketAdapter(JavaPlugin plugin) {
        super(plugin, PacketType.Play.Server.getInstance().values());
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        modify(packet.getItemModifier(), itemStack -> modifyItem(player, itemStack));
        modify(packet.getChatComponents(), wrappedChatComponent -> translate(player, wrappedChatComponent));
        modify(packet.getChatComponentArrays(), wrappedChatComponents -> {
            for (int i = 0; i < wrappedChatComponents.length; i++) {
                wrappedChatComponents[i] = translate(player, wrappedChatComponents[i]);
            }
            return wrappedChatComponents;
        });
    }

    private <T> void modify(StructureModifier<T> structureModifier, UnaryOperator<T> modificationConsumer)   {
        for (int i = 0; i < structureModifier.getFields().size(); i++) {
            structureModifier.modify(i, modificationConsumer::apply);
        }
    }

    private ItemStack modifyItem(Player player, ItemStack itemStack)    {
        Locale locale = player.locale();
        if(itemStack == null || itemStack.getType() == Material.AIR || !itemStack.hasItemMeta() || itemStack.getItemMeta() == null) {
            return itemStack;
        }
        ItemStack clone = itemStack.clone();
        ItemMeta itemMeta = clone.getItemMeta();
        if(itemMeta.hasDisplayName() && itemMeta.displayName() != null)   {
            itemMeta.displayName(GlobalTranslator.render(Objects.requireNonNull(itemMeta.displayName()), locale));
        }
        if(itemMeta.hasLore() && itemMeta.lore() != null)   {
            itemMeta.lore(Objects.requireNonNull(itemMeta.lore())
                    .stream()
                    .map(component -> GlobalTranslator.render(component, locale))
                    .collect(Collectors.toList())
            );
        }
        clone.setItemMeta(itemMeta);
        return clone;
    }

    private WrappedChatComponent translate(Player player, WrappedChatComponent wrappedChatComponent)   {
        if(wrappedChatComponent == null)    {
            return null;
        }
        return WrappedChatComponent.fromJson(gsonComponentSerializer.serialize(GlobalTranslator.render(gsonComponentSerializer.deserialize(wrappedChatComponent.getJson()), player.locale())));
    }
}
