package de.helixdevs.i18n.paper.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.AbstractStructure;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.Pair;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.helixdevs.i18n.paper.protocollib.util.ReflectiveAdventureComponentConverter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public interface ITranslationAdapter {

    static final GsonComponentSerializer gsonComponentSerializer = GsonComponentSerializer.gson();

    default void handleItem(Player player, AbstractStructure packet, int index) {
        packet.getItemModifier().modify(index, item -> modifyItem(player, item));
    }

    default void handleItemList(Player player, AbstractStructure packet, int index) {
        packet.getItemListModifier().modify(index, itemList ->
                itemList.stream().map(itemStack -> modifyItem(player, itemStack)).collect(Collectors.toList()));
    }

    default void handleItemArray(Player player, AbstractStructure packet, int index) {
        packet.getItemArrayModifier().modify(index, itemArray -> {
            for (int i = 0; i < itemArray.length; i++) {
                itemArray[i] = modifyItem(player, itemArray[i]);
            }
            return itemArray;
        });
    }

    default void handleSlotPair(Player player, AbstractStructure packet, int index) {
        packet.getSlotStackPairLists().modify(index, slotPairs -> {
            for (Pair<EnumWrappers.ItemSlot, ItemStack> slotPair : slotPairs) {
                slotPair.setSecond(modifyItem(player, slotPair.getSecond()));
            }
            return slotPairs;
        });
    }

    default void handleChatComponent(Player player, AbstractStructure packet, int index) {
        packet.getChatComponents().modify(index, wrappedChatComponent -> translate(player, wrappedChatComponent));
    }

    default void handleChatComponent(Player player, PacketType type, PacketContainer packet, int index) {
        if (type == PacketType.Play.Server.CHAT || type == PacketType.Play.Server.DISGUISED_CHAT) {
            packet.getChatComponents().modify(index, wrappedChatComponent -> translate(player, wrappedChatComponent));
            return;
        }
        if (type == PacketType.Play.Server.SYSTEM_CHAT) {
            var read = packet.getStrings().read(0);
            if (read != null) {
                var translate = translate(player, WrappedChatComponent.fromJson(read));
                packet.getStrings().write(0, translate.getJson());
                return;
            }
            var handle = packet.getStructures().read(0).getHandle();
            var component = ReflectiveAdventureComponentConverter.componentToString(handle);
            var translate = translate(player, WrappedChatComponent.fromJson(component));
            packet.getModifier().write(0, null);
            packet.getStrings().write(0, translate.getJson());
        }
    }

    default void handleChatComponentArray(Player player, AbstractStructure packet, int index) {
        packet.getChatComponentArrays().modify(index, wrappedChatComponents -> {
            for (int i = 0; i < wrappedChatComponents.length; i++) {
                wrappedChatComponents[i] = translate(player, wrappedChatComponents[i]);
            }
            return wrappedChatComponents;
        });
    }

    default ItemStack modifyItem(Player player, ItemStack itemStack) {
        Locale locale = player.locale();
        if (itemStack == null || itemStack.getType() == Material.AIR || !itemStack.hasItemMeta() || itemStack.getItemMeta() == null) {
            return itemStack;
        }
        ItemStack clone = itemStack.clone();
        ItemMeta itemMeta = clone.getItemMeta();
        if (itemMeta.hasDisplayName() && itemMeta.displayName() != null) {
            itemMeta.displayName(GlobalTranslator.render(Objects.requireNonNull(itemMeta.displayName()), locale));
        }
        if (itemMeta.hasLore() && itemMeta.lore() != null) {
            itemMeta.lore(Objects.requireNonNull(itemMeta.lore())
                    .stream()
                    .map(component -> GlobalTranslator.render(component, locale))
                    .collect(Collectors.toList())
            );
        }
        clone.setItemMeta(itemMeta);
        return clone;
    }

    default WrappedChatComponent translate(Player player, WrappedChatComponent wrappedChatComponent) {
        if (wrappedChatComponent == null) {
            return null;
        }
        var component = GlobalTranslator.render(gsonComponentSerializer.deserialize(wrappedChatComponent.getJson()), player.locale());
        Bukkit.getLogger().info("Translation:" + gsonComponentSerializer.serialize(component));
        return WrappedChatComponent.fromJson(gsonComponentSerializer.serialize(
                component
        ));
    }

}
