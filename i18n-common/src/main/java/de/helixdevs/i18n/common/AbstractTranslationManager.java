package de.helixdevs.i18n.common;

import de.helixdevs.i18n.api.TranslationManager;
import de.helixdevs.i18n.api.TranslationRepository;

public abstract class AbstractTranslationManager implements TranslationManager {

    private final TranslationRepository translationRepository;

    public AbstractTranslationManager(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Override
    public TranslationRepository getTranslationRepository() {
        return translationRepository;
    }
}
