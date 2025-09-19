package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class DateTimeYml
    extends InputYml<String>
{
    public Boolean timezone;

    public DateTimeYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "timezone" ) != null )
        {
            timezone = config.getValue( "timezone", Boolean.class );
        }
    }
}
