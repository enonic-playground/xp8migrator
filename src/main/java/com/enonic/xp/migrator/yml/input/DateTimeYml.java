package com.enonic.xp.migrator.yml.input;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateTimeYml
    extends InputYml<String>
{
    public Boolean timezone;

    public DateTimeYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getValue( "timezone" ) != null )
        {
            timezone = inputTypeConfig.getValue( "timezone", Boolean.class );
        }

        setConfig( source, "timezone" );
    }
}
