package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class ContentTypeFilterYml
    extends InputYml<String>
{
    public Boolean context;

    public ContentTypeFilterYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "context" ) != null )
        {
            context = config.getValue( "context", Boolean.class );
        }
    }
}
