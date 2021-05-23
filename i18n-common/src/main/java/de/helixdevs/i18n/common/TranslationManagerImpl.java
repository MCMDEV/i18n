package de.helixdevs.i18n.common;

import de.helixdevs.i18n.api.TranslationManager;
import de.helixdevs.i18n.api.TranslationConsumer;
import de.helixdevs.i18n.api.TranslationRepository;

import java.util.Set;

public class TranslationManagerImpl implements TranslationManager {

    private final Set<TranslationRepository> translationRepositories;
    private final Set<TranslationConsumer> translationConsumers;

    public TranslationManagerImpl(Set<TranslationRepository> translationRepositories, Set<TranslationConsumer> translationConsumers) {
        this.translationRepositories = translationRepositories;
        this.translationConsumers = translationConsumers;
    }

    @Override
    public Set<TranslationRepository> getTranslationRepositories() {
        return translationRepositories;
    }

    @Override
    public void registerToPlatforms()    {
        translationConsumers.forEach(translationConsumer -> {
            translationConsumer.register(translationRepositories);
        });
    }
}
