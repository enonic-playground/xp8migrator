package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class CheckBoxYml
    extends InputYml<Boolean>
{
    public String alignment;

    public CheckBoxYml( final Input source )
    {
        super( source, Boolean.class );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "alignment" ) != null )
        {
            alignment = config.getValue( "alignment" );
        }
    }
}
