package de.helixdevs.i18n.velocity;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import de.helixdevs.i18n.adventure.AdventureTranslationConsumer;
import de.helixdevs.i18n.api.TranslationConsumer;
import de.helixdevs.i18n.common.command.api.Command;
import de.helixdevs.i18n.common.platform.I18nPlatform;
import de.helixdevs.i18n.common.platform.I18nPlugin;
import de.helixdevs.i18n.velocity.command.VelocitySender;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Plugin(
        id = "i18n",
        name = "I18n",
        version = "1.1",
        description = "Velocity implementation of I18n",
        authors = {"MCMDEV"}
)
public class I18nVelocityPlugin implements I18nPlugin {

    private final ProxyServer proxyServer;
    private final Logger logger;
    private final Path dataDirectory;
    private final I18nPlatform platform;

    private ConfigurationNode configurationNode;

    @Inject
    public I18nVelocityPlugin(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory)   {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        this.platform = new I18nPlatform(this);
    }

    @Subscribe
    private void onProxyInitialization(ProxyInitializeEvent event) throws IOException {
        YAMLConfigurationLoader configurationLoader = YAMLConfigurationLoader.builder()
                .setFlowStyle(DumperOptions.FlowStyle.FLOW)
                .setIndent(2)
                .setPath(dataDirectory.resolve("config.yml"))
                .build();
        this.configurationNode = configurationLoader.load();

        platform.enable();
    }

    @Subscribe
    private void onProxyShutdown(ProxyShutdownEvent event)  {
        platform.disable();
    }

    @Override
    public String getConfigString(String key) {
        return configurationNode.getNode(key).getString();
    }

    @Override
    public List<String> getConfigStringList(String key) {
        try {
            return configurationNode.getNode(key).getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Set<TranslationConsumer> getConsumers() {
        Set<TranslationConsumer> consumers = new HashSet<>();
        consumers.add(new AdventureTranslationConsumer());
        return consumers;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public void registerCommand(Command command) {
        proxyServer.getCommandManager().register(command.getLabel(), new SimpleCommand() {
            @Override
            public void execute(Invocation invocation) {
                command.execute(new VelocitySender(invocation.source()), invocation.arguments());
            }

            @Override
            public boolean hasPermission(Invocation invocation) {
                return invocation.source().hasPermission(command.getPermission());
            }
        });
    }
}
