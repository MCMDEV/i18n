package de.helixdevs.i18n.adventure;

import de.helixdevs.i18n.api.TranslationConsumer;
import de.helixdevs.i18n.api.TranslationRepository;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;

import java.util.Set;

public class AdventureTranslationConsumer implements TranslationConsumer {

    private final GlobalTranslator globalTranslator = GlobalTranslator.translator();
    private final TranslationRegistry adventureRegistry = TranslationRegistry.create(Key.key("i18n", "main"));

    @Override
    public void register(Set<TranslationRepository> repositories) {
        repositories.forEach(translationRepository ->
                translationRepository.getTranslations().forEach(translationEntry ->
                        adventureRegistry.register(translationEntry.getKey(),
                                translationEntry.getLocale(),
                                translationEntry.getMessageFormat()
                        )
                )
        );
        globalTranslator.addSource(adventureRegistry);
    }

    @Override
    public void unregister(Set<TranslationRepository> repositories) {
        repositories.forEach(translationRepository ->
                translationRepository.getTranslations().forEach(translationEntry ->
                        adventureRegistry.unregister(translationEntry.getKey())));
        globalTranslator.removeSource(adventureRegistry);
    }
}
