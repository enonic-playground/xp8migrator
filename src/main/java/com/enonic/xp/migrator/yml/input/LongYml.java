package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class LongYml
    extends InputYml
{
    public Long min;

    public Long max;

    public LongYml( final Input source )
    {
        super( source );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "min" ) != null )
        {
            min = config.getValue( "min", Long.class );
        }

        if ( config.getValue( "max" ) != null )
        {
            max = config.getValue( "max", Long.class );
        }
    }
}
