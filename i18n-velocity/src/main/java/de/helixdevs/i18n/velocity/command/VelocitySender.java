package de.helixdevs.i18n.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import de.helixdevs.i18n.common.command.api.Sender;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class VelocitySender implements Sender {

    private final CommandSource holder;

    public VelocitySender(CommandSource holder) {
        this.holder = holder;
    }

    @Override
    public void sendMessage(String message) {
        holder.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(message));
    }
}
