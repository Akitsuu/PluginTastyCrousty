package me.akitsu.tastyCroustyPlugin.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import me.akitsu.tastyCroustyPlugin.item.TastyCroustyFood;
import org.bukkit.entity.Player;

/**
 * /tastycrousty [quantite] : donne des Tasty Crousty au joueur.
 */
public final class TastyCroustyCommand implements BasicCommand {

    private static final int MAX_AMOUNT = 64;

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (!(source.getExecutor() instanceof Player player)) {
            source.getSender().sendPlainMessage("Seul un joueur peut utiliser cette commande.");
            return;
        }

        int amount = 1;
        if (args.length > 0) {
            try {
                amount = Math.clamp(Integer.parseInt(args[0]), 1, MAX_AMOUNT);
            } catch (NumberFormatException e) {
                player.sendPlainMessage("Quantite invalide : " + args[0]);
                return;
            }
        }

        player.getInventory().addItem(TastyCroustyFood.create(amount));
    }
}
