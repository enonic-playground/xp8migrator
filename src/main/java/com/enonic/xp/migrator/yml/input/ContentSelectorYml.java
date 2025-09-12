package com.enonic.xp.migrator.yml.input;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class ContentSelectorYml
    extends InputYml
{
    public List<String> allowContentType;

    public List<String> allowPath;

    public Boolean treeMode;

    public Boolean hideToggleIcon;

    public ContentSelectorYml( final Input source )
    {
        super( source );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "treeMode" ) != null )
        {
            treeMode = config.getValue( "treeMode", Boolean.class );
        }

        if ( config.getValue( "hideToggleIcon" ) != null )
        {
            hideToggleIcon = config.getValue( "hideToggleIcon", Boolean.class );
        }

        final Set<InputTypeProperty> allowContentTypeValues = config.getProperties( "allowContentType" );
        if ( allowContentTypeValues != null )
        {
            allowContentType = allowContentTypeValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() );
        }

        final Set<InputTypeProperty> allowPathValues = config.getProperties( "allowPath" );
        if ( allowPathValues != null )
        {
            allowPath = allowPathValues.stream().map( InputTypeProperty::getValue ).collect( Collectors.toList() );
        }
    }
}
