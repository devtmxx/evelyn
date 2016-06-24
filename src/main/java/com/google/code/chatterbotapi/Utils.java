package com.google.code.chatterbotapi;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class Utils {
    public static String parametersToWWWFormUrlEncoded( Map< String, String > parameters ) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();

        for ( Map.Entry< String, String > parameter : parameters.entrySet() ) {
            if ( stringBuilder.length() > 0 ) {
                stringBuilder.append( "&" );
            }

            stringBuilder.append( URLEncoder.encode( parameter.getKey(), "UTF-8" ) )
                    .append( "=" )
                    .append( URLEncoder.encode( parameter.getValue(), "UTF-8" ) );
        }

        return stringBuilder.toString();
    }

    public static String md5( String input ) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance( "MD5" );
        messageDigest.update( input.getBytes() );
        BigInteger hash = new BigInteger( 1, messageDigest.digest() );
        return String.format( "%1$032X", hash );
    }

    public static String toAcceptLanguageTags( Locale... locales ) {
        if ( locales.length == 0 ) {
            return "";
        }
        float qf = 1F / ( float ) locales.length;
        float q = 1F;
        StringBuilder stringBuilder = new StringBuilder();

        for ( int i = 0; i < locales.length; i++ ) {
            Locale locale = locales[ i ];
            if ( stringBuilder.length() > 0 ) {
                stringBuilder.append( ", " );
            }
            if ( !locale.getCountry().equals( "" ) ) {
                stringBuilder.append( locale.getLanguage() )
                        .append( "-" )
                        .append( locale.getCountry() )
                        .append( ";q=" )
                        .append( q )
                        .append( ", " )
                        .append( locale.getLanguage() )
                        .append( ";q=" )
                        .append( ( q - 0.01 ) );
            } else {
                stringBuilder.append( locale.getLanguage() )
                        .append( ";q=" )
                        .append( q );
            }

            q -= qf;
        }

        return stringBuilder.toString();
    }

    public static String request( String url, Map<String, String> headers, Map<String, String> cookies, Map<String, String> parameters ) throws Exception {
        HttpURLConnection httpURLConnection = ( HttpURLConnection ) new URL( url ).openConnection();
        httpURLConnection.setRequestProperty( "User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebkit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36" );

        if ( headers != null ) {
            for ( Map.Entry< String, String > header : headers.entrySet() ) {
                httpURLConnection.setRequestProperty( header.getKey(), header.getValue() );
            }
        }

        if ( cookies != null && !cookies.isEmpty() ) {
            StringBuilder stringBuilder = new StringBuilder();
            for ( String cookie : cookies.values() ) {
                if ( stringBuilder.length() > 0 ) {
                    stringBuilder.append( ";" );
                }
                stringBuilder.append( cookie );
            }
            httpURLConnection.setRequestProperty( "Cookie", stringBuilder.toString() );
        }

        httpURLConnection.setDoInput( true );

        if ( parameters != null && !parameters.isEmpty() ) {
            httpURLConnection.setDoOutput( true );
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter( httpURLConnection.getOutputStream() );
            outputStreamWriter.write( parametersToWWWFormUrlEncoded( parameters ) );
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }

        if ( cookies != null ) {
            for ( Map.Entry< String, List< String > > entry : httpURLConnection.getHeaderFields().entrySet() ) {
                if ( entry != null && entry.getKey() != null && entry.getKey().equalsIgnoreCase( "Set-Cookie" ) ) {
                    for ( String header : entry.getValue() ) {
                        for ( HttpCookie httpCookie : HttpCookie.parse( header ) ) {
                            cookies.put( httpCookie.getName(), httpCookie.toString() );
                        }
                    }
                }
            }
        }

        Reader reader = new BufferedReader( new InputStreamReader( httpURLConnection.getInputStream() ) );
        StringWriter stringWriter = new StringWriter();
        char[] buffer = new char[ 1024 ];
        int n = 0;

        while ( ( n = reader.read( buffer ) ) != -1 ) {
            stringWriter.write( buffer, 0, n );
        }

        reader.close();
        return stringWriter.toString();
    }

    public static String xPathSearch( String input, String expression ) throws Exception {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = xPath.compile( expression );
        Document document = documentBuilder.parse( new ByteArrayInputStream( input.getBytes( "UTF-8" ) ) );
        String output = ( String ) xPathExpression.evaluate( document, XPathConstants.STRING );
        return output == null ? "" : output.trim();
    }

    public static String stringAtIndex( String[] strings, int index ) {
        if ( index >= strings.length ) {
            return "";
        }
        return strings[ index ];
    }
}