package com.google.code.chatterbotapi;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/*
    chatter-bot-api
    Copyright (C) 2011 pierredavidbelanger@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
public class Pandorabots implements ChatterBot {
    private final String botId;

    public Pandorabots( String botId ) {
        this.botId = botId;
    }
    @Override
    public ChatterBotSession createSession( Locale... locales ) {
        return null;
    }

    private class Session implements ChatterBotSession {
        private final Map< String, String > vars;

        public Session() {
            this.vars = new LinkedHashMap<>();
            this.vars.put( "botid", botId );
            this.vars.put( "custid", UUID.randomUUID().toString() );
        }

        @Override
        public ChatterBotThought think( ChatterBotThought chatterBotThought ) throws Exception {
            this.vars.put( "input", chatterBotThought.getText() );

            String response = Utils.request( "http://pandorabots.com/pandora/talk-xml", null, null, this.vars );

            ChatterBotThought responseThought = new ChatterBotThought();
            responseThought.setText( Utils.xPathSearch( response, "//result/that/text()" ) );

            return responseThought;
        }

        @Override
        public String think( String text ) throws Exception {
            ChatterBotThought chatterBotThought = new ChatterBotThought();
            chatterBotThought.setText( text );
            return this.think( chatterBotThought ).getText();
        }
    }
}
