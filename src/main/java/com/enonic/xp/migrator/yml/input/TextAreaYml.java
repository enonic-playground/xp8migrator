package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class TextAreaYml
    extends InputYml
{
    public Integer maxLength;

    public Boolean showCounter;

    public TextAreaYml( final Input source )
    {
        super( source );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "maxLength" ) != null )
        {
            maxLength = config.getValue( "maxLength", Integer.class );
        }

        if ( config.getValue( "showCounter" ) != null )
        {
            showCounter = config.getValue( "showCounter", Boolean.class );
        }
    }
}
