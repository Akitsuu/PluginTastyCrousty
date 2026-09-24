package me.akitsu.tastyCroustyPlugin.resourcepack;

import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Properties;
import java.util.UUID;

/**
 * Envoie aux joueurs le resource pack heberge sur GitHub. Le hash est calcule
 * a partir de la copie embarquee dans le jar, identique a celle publiee.
 */
public final class ResourcePackSender implements Listener {

    private final ResourcePackRequest request;

    public ResourcePackSender(JavaPlugin plugin) throws IOException {
        String url = readProperties(plugin).getProperty("url");
        String hash = sha1(readResource(plugin, "resourcepack.zip"));

        ResourcePackInfo info = ResourcePackInfo.resourcePackInfo()
                .id(UUID.nameUUIDFromBytes(("tastycrousty:" + hash).getBytes(StandardCharsets.UTF_8)))
                .uri(URI.create(url))
                .hash(hash)
                .build();
        this.request = ResourcePackRequest.resourcePackRequest()
                .packs(info)
                .required(false)
                .prompt(Component.text("Texture du Tasty Crousty"))
                .build();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendResourcePacks(request);
    }

    private static Properties readProperties(JavaPlugin plugin) throws IOException {
        Properties properties = new Properties();
        try (InputStream in = open(plugin, "resourcepack.properties")) {
            properties.load(in);
        }
        return properties;
    }

    private static byte[] readResource(JavaPlugin plugin, String name) throws IOException {
        try (InputStream in = open(plugin, name)) {
            return in.readAllBytes();
        }
    }

    private static InputStream open(JavaPlugin plugin, String name) throws IOException {
        InputStream in = plugin.getResource(name);
        if (in == null) {
            throw new IOException(name + " introuvable dans le jar");
        }
        return in;
    }

    private static String sha1(byte[] data) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-1").digest(data));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
