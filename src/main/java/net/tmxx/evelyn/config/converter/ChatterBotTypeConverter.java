package net.tmxx.evelyn.config.converter;

import com.google.code.chatterbotapi.ChatterBotType;
import net.cubespace.Yamler.Config.Converter.Converter;
import net.cubespace.Yamler.Config.InternalConverter;

import java.lang.reflect.ParameterizedType;

/**
 * <p>
 *     Converts the chatter bot type enumeration to a valid config object
 *     and the other way around.
 * </p>
 *
 * @author tmxx
 * @version 1.0
 */
public class ChatterBotTypeConverter implements Converter {
    /**
     * Constructs a new chatter bot type converter by specifying an internal
     * converter to use.
     *
     * @param internalConverter The internal converter.
     */
    public ChatterBotTypeConverter( InternalConverter internalConverter ) {}

    /**
     * Converts the chatter bot type to a configuration object.
     *
     * @param type              The chatter bot class type.
     * @param obj               The chatter bot type to convert a configuration object.
     * @param parameterizedType The parameterized type of the chatter bot type.
     * @return                  The configuration object.
     * @throws Exception        If an error occurs.
     */
    @Override
    public Object toConfig( Class< ? > type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        ChatterBotType chatterBotType = ( ChatterBotType ) obj;
        return chatterBotType.name();
    }

    /**
     * Converts the chatter bot type from the config object to a chatter bot type.
     *
     * @param type              The type to which this converter should convert the config object to.
     * @param obj               The configuration object to convert.
     * @param parameterizedType The parameterized type of the configuration object.
     * @return                  The chatter bot type.
     * @throws Exception        If an error occurs.
     */
    @Override
    public Object fromConfig( Class< ? > type, Object obj, ParameterizedType parameterizedType ) throws Exception {
        return ChatterBotType.valueOf( obj.toString() );
    }

    /**
     * Checks whether this converter supports the specified class type.
     *
     * @param type  The class type to check.
     * @return      Whether this converter supports the class type.
     */
    @Override
    public boolean supports( Class< ? > type ) {
        return type.isAssignableFrom( ChatterBotType.class );
    }
}
