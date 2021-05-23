package de.helixdevs.i18n.adventure;

import de.helixdevs.i18n.api.TranslationConsumer;
import de.helixdevs.i18n.api.TranslationRepository;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;

import java.util.Locale;
import java.util.Set;

public class AdventureTranslationConsumer implements TranslationConsumer {
    @Override
    public void register(Set<TranslationRepository> repositories) {
        GlobalTranslator globalTranslator = GlobalTranslator.get();

        repositories.forEach(translationRepository -> {
            TranslationRegistry adventureRegistry = TranslationRegistry.create(Key.key("i18n", translationRepository.getIdentifier()));
            translationRepository.getTranslations().forEach(translationEntry -> {
                adventureRegistry.register(translationEntry.getKey(), translationEntry.getLocale(), translationEntry.getMessageFormat());
            });

            globalTranslator.addSource(adventureRegistry);
        });
    }
}
