package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class DoubleYml
    extends InputYml<Double>
{
    public Double min;

    public Double max;

    public DoubleYml( final Input source )
    {
        super( source, Double.class );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "min" ) != null )
        {
            min = config.getValue( "min", Double.class );
        }

        if ( config.getValue( "max" ) != null )
        {
            max = config.getValue( "max", Double.class );
        }
    }
}
