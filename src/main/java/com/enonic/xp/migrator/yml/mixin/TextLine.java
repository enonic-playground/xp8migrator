package com.enonic.xp.migrator.yml.mixin;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class TextLine
    extends InputYml
{
    public Integer maxLength;

    public String regexp;

    public Boolean showCounter;

    public TextLine( final Input source )
    {
        super( source );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "maxLength" ) != null )
        {
            maxLength = config.getValue( "maxLength", Integer.class );
        }

        if ( config.getValue( "regexp" ) != null )
        {
            regexp = config.getValue( "regexp", String.class );
        }

        if ( config.getValue( "showCounter" ) != null )
        {
            showCounter = config.getValue( "showCounter", Boolean.class );
        }
    }
}
