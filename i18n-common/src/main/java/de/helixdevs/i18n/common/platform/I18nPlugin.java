package de.helixdevs.i18n.common.platform;

import de.helixdevs.i18n.api.TranslationConsumer;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public interface I18nPlugin {

    String getConfigString(String key);

    List<String> getConfigStringList(String key);

    Set<TranslationConsumer> getConsumers();

    Logger getLogger();
}
