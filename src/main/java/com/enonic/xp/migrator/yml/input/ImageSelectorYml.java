package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageSelectorYml
    extends InputYml<String>
{
    public ImageSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        if ( inputTypeConfig.getSize() > 0 )
        {
            config = new LinkedHashMap<>();

            if ( inputTypeConfig.getValue( "treeMode" ) != null )
            {
                config.put( "treeMode", inputTypeConfig.getValue( "treeMode", Boolean.class ) );
            }

            if ( inputTypeConfig.getValue( "hideToggleIcon" ) != null )
            {
                config.put( "hideToggleIcon", inputTypeConfig.getValue( "hideToggleIcon", Boolean.class ) );
            }

            final Set<InputTypeProperty> allowPathValues = inputTypeConfig.getProperties( "allowPath" );
            if ( !allowPathValues.isEmpty() )
            {
                config.put( "allowPath", allowPathValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() ) );
            }

            setConfig( source, "treeMode", "hideToggleIcon", "allowPath" );
        }
    }
}
