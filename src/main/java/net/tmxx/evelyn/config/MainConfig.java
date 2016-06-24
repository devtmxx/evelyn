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

import com.google.code.chatterbotapi.ChatterBotType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.cubespace.Yamler.Config.Comments;
import net.cubespace.Yamler.Config.Config;
import net.cubespace.Yamler.Config.InvalidConverterException;
import net.tmxx.evelyn.Evelyn;
import net.tmxx.evelyn.config.converter.ChatterBotTypeConverter;
import net.tmxx.evelyn.config.converter.LocaleConverter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>
 *     The main config used by the evelyn chatter bot to store
 *     important information.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
@ToString
public class MainConfig extends Config {
    /**
     * The allowed locales for evelyn.
     */
    @Comments( {
            "The allowed locales for evelyn."
    } )
    private List<Locale> locales = new ArrayList<Locale>() { {
        add( Locale.US );
        add( Locale.GERMANY );
    } };

    /**
     * The bot type of evelyn.
     */
    @Comments( {
            "The bot type of evelyn. Available types are:",
            "   - CLEVERBOT",
            "   - JABBERWACKY",
            "   - PANDORABOTS"
    } )
    private ChatterBotType type = ChatterBotType.CLEVERBOT;

    /**
     * The bot id of evelyn. Only necessary if type is PANDORABOTS.
     */
    @Comments( {
            "The bot id of evelyn. Only necessary if type is PANDORABOTS"
    } )
    private String botId = "unknown";

    /**
     * Constructs a new main configuration for the evelyn chatter bot plugin.
     *
     * @param evelyn    The evelyn plugin instance.
     */
    public MainConfig( Evelyn evelyn ) {
        this.CONFIG_FILE = new File( evelyn.getDataFolder(), "config.yml" );

        try {
            this.addConverter( ChatterBotTypeConverter.class );
            this.addConverter( LocaleConverter.class );
        } catch ( InvalidConverterException e ) {
            e.printStackTrace();
        }
    }
}
