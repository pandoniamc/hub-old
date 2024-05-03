package fr.pandonia.hub.guis.main;

import fr.pandonia.hub.api.player.PandoniaPlayer;
import fr.pandonia.hub.guis.ChildGui;
import fr.pandonia.hub.guis.GuiType;
import fr.pandonia.hub.guis.enums.Game;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.BiFunction;

public class GameGui<T> extends ChildGui {

    private static final int[] BACKGROUND_SLOTS = {0, 1, 7, 8, 9, 17, 36, 44, 45, 46, 52, 53};

    public GameGui(PandoniaPlayer player, Game game, List<T> servers, BiFunction<GameGui<T>, T, ItemStack> itemFunction) {
        super(6, game.getName(), player, GuiType.MAIN);

        setBackground(game.getDyeColor().ordinal(), BACKGROUND_SLOTS);

        setReturn(49);

        for (int i = 0; i < servers.size(); i++) {
            int row = 11 + (i / 5) * 9;
            int column = i % 5;

            setItem(row + column, itemFunction.apply(this, servers.get(i)));
        }
    }
}
