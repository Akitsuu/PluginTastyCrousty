package me.akitsu.tastyCroustyPlugin;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.akitsu.tastyCroustyPlugin.command.TastyCroustyCommand;
import me.akitsu.tastyCroustyPlugin.item.TastyCroustyFood;
import me.akitsu.tastyCroustyPlugin.resourcepack.ResourcePackSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class TastyCroustyPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().addRecipe(TastyCroustyFood.createRecipe(this));
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
                event.registrar().register("tastycrousty", "Donne des Tasty Crousty", new TastyCroustyCommand()));

        try {
            getServer().getPluginManager().registerEvents(new ResourcePackSender(this), this);
        } catch (IOException e) {
            getLogger().severe("Impossible de charger le resource pack : " + e.getMessage());
        }
    }
}
