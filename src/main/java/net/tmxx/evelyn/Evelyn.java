/**
 * Evelyn is a plugin that provides a chatter bot for minecraft servers.
 * Copyright (C) 2016  tmxx
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tmxx.evelyn;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import lombok.Getter;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import net.tmxx.evelyn.command.EvelynCommand;
import net.tmxx.evelyn.config.MainConfig;
import net.tmxx.evelyn.config.MessageConfig;
import net.tmxx.evelyn.listener.PlayerJoinListener;
import net.tmxx.evelyn.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *     The main entry point for the evelyn plugin.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class Evelyn extends JavaPlugin {
    /**
     * Map of all player uuids and their connection to evelyn.
     */
    @Getter private Map<UUID, ChatterBotSession> connections = new ConcurrentHashMap<>();

    /**
     * The main config of this plugin.
     */
    @Getter private MainConfig mainConfig;

    /**
     * The message config of this plugin.
     */
    @Getter private MessageConfig messageConfig;

    /**
     * Whether evelyn is online.
     */
    @Getter private boolean online = false;

    /**
     * The chatter bot to use.
     */
    private ChatterBot chatterBot;

    /**
     * Invoked when this plugin is being enabled.
     */
    @Override
    public void onEnable() {
        if ( !this.getDataFolder().exists() && !this.getDataFolder().mkdir() ) {
            this.getLogger().severe( "Could not create data folder." );
            this.getLogger().severe( "Please check your file system permissions." );
            Bukkit.getPluginManager().disablePlugin( this );
            return;
        }

        this.mainConfig = new MainConfig( this );
        this.messageConfig = new MessageConfig( this );

        try {
            this.mainConfig.init();
            this.messageConfig.init();
        } catch ( InvalidConfigurationException e ) {
            e.printStackTrace();
        }

        try {
            this.chatterBot = new ChatterBotFactory().create( this.mainConfig.getType(), this.mainConfig.getBotId() );
            this.online = true;
            this.getLogger().info( "Evelyn service available; using type " + this.mainConfig.getType() );
        } catch ( Exception e ) {
            this.online = false;
            this.getLogger().info( "Evelyn service not available; using type " + this.mainConfig.getType() );
        }

        getServer().getPluginManager().registerEvents( new PlayerJoinListener( this ), this );
        getServer().getPluginManager().registerEvents( new PlayerQuitListener( this ), this );

        getCommand( "evelyn" ).setExecutor( new EvelynCommand( this ) );

        this.getLogger().info( "Evelyn is a plugin that provides a chatter bot for minecraft servers.\n" +
                "    Copyright (C) 2016  tmxx\n" +
                "\n" +
                "    This program is free software: you can redistribute it and/or modify\n" +
                "    it under the terms of the GNU General Public License as published by\n" +
                "    the Free Software Foundation, either version 3 of the License, or\n" +
                "    (at your option) any later version.\n" +
                "\n" +
                "    This program is distributed in the hope that it will be useful,\n" +
                "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n" +
                "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n" +
                "    GNU General Public License for more details.\n" +
                "\n" +
                "    You should have received a copy of the GNU General Public License\n" +
                "    along with this program.  If not, see <http://www.gnu.org/licenses/>." );
    }

    /**
     * Creates a chatter bot session for the specified player.
     *
     * @param player    The player to create a session for.
     */
    public void createSession( Player player ) {
        if ( this.online ) {
            this.getLogger().info( "Creating session for user " + player.getName() + ":" + player.getUniqueId() );
            Bukkit.getScheduler().runTaskAsynchronously( this, new Runnable() {
                @Override
                public void run() {
                    connections.put( player.getUniqueId(),
                            chatterBot.createSession(
                                    mainConfig.getLocales().toArray( new Locale[ mainConfig.getLocales().size() ] ) ) );
                }
            } );
        }
    }
}
