package de.helixdevs.i18n.api;

import java.util.Set;

public interface TranslationManager {

    Set<TranslationRepository> getTranslationRepositories();

    void registerToPlatforms();

}
