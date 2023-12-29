
/*
 * This file is part of adventure, licensed under the MIT License.
 *
 * Copyright (c) 2017-2021 KyoriPowered
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.helixdevs.i18n.common.repositories;

import de.helixdevs.i18n.api.TranslationEntry;
import de.helixdevs.i18n.api.TranslationRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

public class FilesTranslationRepository implements TranslationRepository {

    private final Path rootPath;
    private final Set<TranslationEntry> translationEntries = new HashSet<>();

    public FilesTranslationRepository(Path rootPath) {
        this.rootPath = rootPath;
        load();
    }

    private void load() {
        try (Stream<Path> pathStream = Files.walk(rootPath, 16)) {
            pathStream.forEach(path -> {
                File file = path.toFile();
                String fileName = file.getName();
                if (!fileName.endsWith(".properties")) {
                    return;
                }

                fileName = fileName.substring(0, fileName.length() - ".properties".length());

                Locale locale = parseLocale(fileName);
                try {
                    PropertyResourceBundle resourceBundle;
                    try (BufferedReader reader = Files.newBufferedReader(path)) {
                        resourceBundle = new PropertyResourceBundle(reader);
                    }

                    resourceBundle.keySet().forEach(key -> {
                        translationEntries.add(new TranslationEntry(
                                locale,
                                key,
                                new MessageFormat((String) resourceBundle.getObject(key), locale)
                        ));
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getIdentifier() {
        return "files";
    }

    @Override
    public Set<TranslationEntry> getTranslations() {
        return translationEntries;
    }

    /**
     * Shamelessly lifted from https://github.com/KyoriPowered/adventure/blob/master/api/src/main/java/net/kyori/adventure/translation/Translator.java
     * Parses a {@link Locale} from a {@link String}.
     *
     * @param string the string
     * @return a locale
     * @since 4.0.0
     */
    private static Locale parseLocale(final String string) {
        final String[] segments = string.split("_", 3); // language_country_variant
        final int length = segments.length;
        if (length == 1) {
            return new Locale(string); // language
        } else if (length == 2) {
            return new Locale(segments[0], segments[1]); // language + country
        } else if (length == 3) {
            return new Locale(segments[0], segments[1], segments[2]); // language + country + variant
        }
        return null;
    }
}
