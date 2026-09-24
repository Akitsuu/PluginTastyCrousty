package me.akitsu.tastyCroustyPlugin.item;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Consumable;
import io.papermc.paper.datacomponent.item.FoodProperties;
import io.papermc.paper.datacomponent.item.consumable.ItemUseAnimation;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public final class TastyCroustyFood {

    private static final int NUTRITION = 10;
    private static final float SATURATION = 6f;
    private static final float EAT_SECONDS = 1.6f;
    private static final Key MODEL = Key.key("tastycrousty", "tasty_crousty");

    private TastyCroustyFood() {
    }

    public static ItemStack create(int amount) {
        ItemStack item = ItemStack.of(Material.COOKIE, amount);
        item.setData(DataComponentTypes.ITEM_NAME, Component.text("Tasty Crousty", NamedTextColor.GOLD));
        item.setData(DataComponentTypes.ITEM_MODEL, MODEL);
        item.setData(DataComponentTypes.FOOD, FoodProperties.food()
                .nutrition(NUTRITION)
                .saturation(SATURATION)
                .build());
        item.setData(DataComponentTypes.CONSUMABLE, Consumable.consumable()
                .consumeSeconds(EAT_SECONDS)
                .animation(ItemUseAnimation.EAT)
                .build());
        return item;
    }

    /** Recette bol + sucre + poulet  */
    public static ShapelessRecipe createRecipe(Plugin plugin) {
        ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, "tasty_crousty"), create(1));
        recipe.addIngredient(Material.BOWL);
        recipe.addIngredient(Material.SUGAR);
        recipe.addIngredient(new RecipeChoice.MaterialChoice(Material.CHICKEN, Material.COOKED_CHICKEN));
        return recipe;
    }
}
