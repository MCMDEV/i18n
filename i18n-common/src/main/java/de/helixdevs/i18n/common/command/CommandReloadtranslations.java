package de.helixdevs.i18n.common.command;

import de.helixdevs.i18n.common.command.api.Command;
import de.helixdevs.i18n.common.command.api.Sender;
import de.helixdevs.i18n.common.platform.I18nPlatform;

public class CommandReloadtranslations extends Command {

    private final I18nPlatform i18nPlatform;

    public CommandReloadtranslations(I18nPlatform i18nPlatform) {
        super("reloadtranslations", "i18n.reloadtranslations");
        this.i18nPlatform = i18nPlatform;
    }

    @Override
    public void execute(Sender sender, String[] arguments) {
        i18nPlatform.reload();
        sender.sendMessage("§aTranslations reloaded.");
    }
}
