package de.helixdevs.i18n.common.repositories;

import de.helixdevs.i18n.api.TranslationEntry;
import de.helixdevs.i18n.api.TranslationRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class FilesTranslationRepository implements TranslationRepository {

    private final Path rootPath;
    private final Set<TranslationEntry> translationEntries = new HashSet<>();

    public FilesTranslationRepository(Path rootPath) {
        this.rootPath = rootPath;
        load();
    }

    private void load() {
        try {
            Files.walk(rootPath, 16).forEach(path -> {

            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<TranslationEntry> getTranslations() {
        return translationEntries;
    }
}
