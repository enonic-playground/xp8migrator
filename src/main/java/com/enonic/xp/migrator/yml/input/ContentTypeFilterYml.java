package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentTypeFilterYml
    extends InputYml<String>
{
    public ContentTypeFilterYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getValue( "context" ) != null )
        {
            config = new LinkedHashMap<>();
            config.put( "context", inputTypeConfig.getValue( "context", Boolean.class ) );
        }
    }
}
