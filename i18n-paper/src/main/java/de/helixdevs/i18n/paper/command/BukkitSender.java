package de.helixdevs.i18n.paper.command;

import de.helixdevs.i18n.common.command.api.Sender;
import org.bukkit.command.CommandSender;

public class BukkitSender implements Sender {

    private final CommandSender holder;

    public BukkitSender(CommandSender holder) {
        this.holder = holder;
    }

    @Override
    public void sendMessage(String message) {
        holder.sendMessage(message);
    }
}
