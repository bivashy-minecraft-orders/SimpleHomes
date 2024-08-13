package dev.majek.simplehomes.data;

import dev.majek.simplehomes.SimpleHomes;
import dev.majek.simplehomes.data.struct.Home;
import org.bukkit.entity.Player;
import ru.abstractmenus.api.Catalog;
import ru.abstractmenus.api.Types;
import ru.abstractmenus.api.ValueExtractor;
import ru.abstractmenus.api.inventory.Menu;
import ru.abstractmenus.hocon.api.ConfigNode;
import ru.abstractmenus.hocon.api.serialize.NodeSerializeException;
import ru.abstractmenus.hocon.api.serialize.NodeSerializer;

import java.util.Collection;

public class GuiHomeCatalog implements Catalog<Home> {

    @Override
    public Collection<Home> snapshot(Player player, Menu menu) {
        return SimpleHomes.core().getHomesPlayer(player.getUniqueId()).getHomes();
    }

    @Override
    public ValueExtractor extractor() {
        return (object, placeholder) -> {
            if (object instanceof Home) {
                Home home = (Home) object;
                if (placeholder.equals("name"))
                    return home.name();
            }
            return null;
        };
    }

    public static void register() {
        Types.registerCatalog("home", GuiHomeCatalog.class, new Serializer());
    }

    public static class Serializer implements NodeSerializer<GuiHomeCatalog> {

        @Override
        public GuiHomeCatalog deserialize(Class<GuiHomeCatalog> aClass, ConfigNode configNode) throws NodeSerializeException {
            return new GuiHomeCatalog();
        }

    }

}
