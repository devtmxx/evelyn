package net.tmxx.evelyn.config.converter;

import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;

import java.lang.reflect.ParameterizedType;
import java.util.Locale;

/**
 * <p>
 *     Converts a locale to a valid configuration object and
 *     the other way around.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class LocaleConverter implements Converter {
    /**
     * Constructs a new locale converter by specifying an internal converter to use.
     *
     * @param internalConverter The internal converter.
     */
    public LocaleConverter( InternalConverter internalConverter ) {}

    /**
     * Converts the locale to a configuration object.
     *
     * @param type              The locale class type.
     * @param obj               The locale to convert a configuration object.
     * @param parameterizedType The parameterized type of locale.
     * @return                  The configuration object.
     * @throws Exception        If an error occurs.
     */
    @Override
    public Object toConfig( Class< ? > type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        Locale locale = ( Locale ) obj;
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    /**
     * Converts the locale from the config object to a locale.
     *
     * @param type              The type to which this converter should convert the config object to.
     * @param obj               The configuration object to convert.
     * @param parameterizedType The parameterized type of the configuration object.
     * @return                  The converted locale.
     * @throws Exception        If an error occurs.
     */
    @Override
    public Object fromConfig( Class< ? > type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        return new Locale( obj.toString().split( "_" )[ 0 ], obj.toString().split( "_" )[ 1 ] );
    }

    /**
     * Checks whether this converter supports the specified class type.
     *
     * @param type  The class type to check.
     * @return      Whether this converter supports the class type.
     */
    @Override
    public boolean supports( Class< ? > type ) {
        return type.isAssignableFrom( Locale.class );
    }
}
