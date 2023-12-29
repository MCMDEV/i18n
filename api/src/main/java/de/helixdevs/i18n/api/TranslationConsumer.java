package de.helixdevs.i18n.api;

import java.util.Set;

public interface TranslationConsumer {

    void register(Set<TranslationRepository> repositories);

    void unregister(Set<TranslationRepository> repositories);
}
