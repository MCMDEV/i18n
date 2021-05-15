package de.helixdevs.i18n.adventure;

import de.helixdevs.i18n.api.TranslationRepository;
import de.helixdevs.i18n.common.AbstractTranslationManager;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;

public class AdventureTranslationManager extends AbstractTranslationManager {

    private final String namespace;
    private TranslationRegistry translationRegistry;

    public AdventureTranslationManager(String namespace, TranslationRepository translationRepository) {
        super(translationRepository);
        this.namespace = namespace;
    }

    @Override
    public void registerToPlatform() {
        GlobalTranslator globalTranslator = GlobalTranslator.get();

        this.translationRegistry = TranslationRegistry.create(Key.key(namespace, "translations"));
        getTranslationRepository().getTranslations().forEach(translationEntry -> {
            translationRegistry.register(translationEntry.getKey(), translationEntry.getLocale(), translationEntry.getMessageFormat());
        });

        globalTranslator.addSource(translationRegistry);
    }
}
