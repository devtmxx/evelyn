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
package net.tmxx.evelyn.command;

import com.google.code.chatterbotapi.ChatterBotSession;
import lombok.RequiredArgsConstructor;
import net.tmxx.evelyn.Evelyn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * <p>
 *     The evelyn command. This command is used by players
 *     to talk to evelyn.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@RequiredArgsConstructor
public class EvelynCommand implements CommandExecutor {
    /**
     * The evelyn plugin instance.
     */
    private final Evelyn evelyn;

    /**
     * Invoked when a command sender dispatches the evelyn command.
     *
     * @param commandSender The command sender who has dispatched the command.
     * @param command       The dispatched command.
     * @param commandLabel  The label of the dispatched command.
     * @param args          The arguments specified by the command sender.
     * @return              Whether the command was successfully executed.
     */
    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( commandSender.hasPermission( "evelyn.command.talk" ) ) {
            if ( commandSender instanceof Player ) {
                Player player = ( Player ) commandSender;

                if ( args.length == 0 ) {
                    player.sendMessage( this.format( this.evelyn.getMessageConfig().getNoArgs() ) );
                    return true;
                } else {
                    if ( !this.evelyn.getConnections().containsKey( player.getUniqueId() ) ) {
                        player.sendMessage( this.format( this.evelyn.getMessageConfig().getEvelynDoesntWant() ) );
                    } else {
                        ChatterBotSession chatterBotSession = this.evelyn.getConnections().get( player.getUniqueId() );
                        StringBuilder stringBuilder = new StringBuilder();

                        for ( String arg : args ) {
                            stringBuilder.append( arg ).append( " " );
                        }

                        player.sendMessage( this.format( this.evelyn.getMessageConfig().getSelfPrefix() + stringBuilder.toString() ) );

                        Bukkit.getScheduler().runTaskAsynchronously( this.evelyn, new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    player.sendMessage( format( evelyn.getMessageConfig().getEvelynPrefix() + chatterBotSession.think( stringBuilder.toString() ) ) );
                                } catch ( Exception e ) {
                                    player.sendMessage( format( evelyn.getMessageConfig().getEvelynDoesntWant() ) );
                                }
                            }
                        } );
                    }
                }
            } else {
                commandSender.sendMessage( this.format( this.evelyn.getMessageConfig().getNoConsole() ) );
            }
        }
        return true;
    }

    /**
     * Translates the input string to a colorized message.
     *
     * @param input The input string to translate.
     * @return      The colorized message.
     */
    private String format( String input ) {
        return ChatColor.translateAlternateColorCodes( '&', input );
    }
}
