package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class TextLineYml
    extends InputYml<String>
{
    public Integer maxLength;

    public String regexp;

    public Boolean showCounter;

    public TextLineYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "maxLength" ) != null )
        {
            maxLength = config.getValue( "maxLength", Integer.class );
        }

        if ( config.getValue( "regexp" ) != null )
        {
            regexp = config.getValue( "regexp" );
        }

        if ( config.getValue( "showCounter" ) != null )
        {
            showCounter = config.getValue( "showCounter", Boolean.class );
        }
    }
}
