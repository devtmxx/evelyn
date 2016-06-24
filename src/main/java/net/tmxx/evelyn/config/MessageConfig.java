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
package net.tmxx.evelyn.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.cubespace.Yamler.Config.Comments;
import net.cubespace.Yamler.Config.Config;
import net.tmxx.evelyn.Evelyn;

import java.io.File;

/**
 * <p>
 *     The message config for the evelyn plugin.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class MessageConfig extends Config {
    /**
     * This message is displayed if a user wants to talk to evelyn
     * but there is no connection.
     */
    @Comments( {
            "This message is displayed if a user wants to talk to evelyn",
            "but there is no connection"
    } )
    private String evelynDoesntWant = "&cHm. It looks like Evelyn doesn't want to talk to you. Maybe try again later.";

    /**
     * This messages is only displayed to users with a specified permission
     * if evelyns service is not available.
     */
    @Comments( {
            "This message is only displayed to users with a specified permission",
            "if evelyns service is not available"
    } )
    private String evelynIsOffline = "§eEvelyn is currently &cnot available. &ePlease check your service status.";

    /**
     * This messages is displayed when evelyn answers to a user.
     */
    @Comments( {
            "This message is displayed when evelyn answers to a user"
    } )
    private String evelynPrefix = "&aEvelyn&7: &f";

    /**
     * This message is displayed when a user is chatting with evelyn.
     */
    @Comments( {
            "This message is displayed when a user is chatting with evelyn"
    } )
    private String selfPrefix = "&eMe&7: &f";

    /**
     * This messages is displayed if the console wants to talk to evelyn.
     */
    @Comments( {
            "This message is displayed if the console wants to talk to evelyn"
    } )
    private String noConsole = "&cEvelyn doesn't want to talk to consoles. Sorry.";

    /**
     * This messages is displayed if a user only types /evelyn in chat.
     */
    @Comments( {
            "This message is displayed if a user only types /evelyn in chat"
    } )
    private String noArgs = "&cEvelyn doesn't like silence. Maybe use &7/&aevelyn [Message]";

    /**
     * Constructs a new main configuration for the evelyn chatter bot plugin.
     *
     * @param evelyn    The evelyn plugin instance.
     */
    public MessageConfig( Evelyn evelyn ) {
        this.CONFIG_FILE = new File( evelyn.getDataFolder(), "messages.yml" );
    }
}
