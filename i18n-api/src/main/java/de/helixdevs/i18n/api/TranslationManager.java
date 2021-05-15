package de.helixdevs.i18n.api;

public interface TranslationManager {

    TranslationRepository getTranslationRepository();

    void registerToPlatform();

}
