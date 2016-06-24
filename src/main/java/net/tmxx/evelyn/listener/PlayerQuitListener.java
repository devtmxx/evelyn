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
package net.tmxx.evelyn.listener;

import lombok.RequiredArgsConstructor;
import net.tmxx.evelyn.Evelyn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * <p>
 *     Listener listening for the {@link org.bukkit.event.player.PlayerQuitEvent} and
 *     removes a user from the connections map.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {
    /**
     * The evelyn plugin instance.
     */
    private final Evelyn evelyn;

    /**
     * Invoked when a player disconnects from the server.
     *
     * @param event The posted event.
     */
    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        if ( this.evelyn.getConnections().containsKey( event.getPlayer().getUniqueId() ) ) {
            this.evelyn.getConnections().remove( event.getPlayer().getUniqueId() );
            this.evelyn.getLogger().info( "Destroying session for user " + event.getPlayer().getName() + ":" + event.getPlayer().getUniqueId() );
        }
    }
}
