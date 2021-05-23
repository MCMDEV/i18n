package de.helixdevs.i18n.common.platform;

import de.helixdevs.i18n.api.TranslationManager;
import de.helixdevs.i18n.api.TranslationRepository;
import de.helixdevs.i18n.common.TranslationManagerImpl;
import de.helixdevs.i18n.common.repositories.FilesTranslationRepository;

import java.nio.file.Paths;
import java.util.HashSet;

public class I18nPlatform {

    private final I18nPlugin plugin;
    private TranslationManager translationManager;

    public I18nPlatform(I18nPlugin plugin) {
        this.plugin = plugin;
    }

    public void enable() {
        HashSet<TranslationRepository> repositories = new HashSet<>();
        repositories.add(new FilesTranslationRepository(Paths.get("i18n_repo")));

        this.translationManager = new TranslationManagerImpl(repositories, plugin.getConsumers());

        this.translationManager.registerToPlatforms();

        plugin.getLogger().info("Registered " + repositories.stream()
                .mapToInt(translationRepository -> translationRepository.getTranslations().size())
                .sum() + " translations."
        );
    }

    public void disable() {
    }
}
