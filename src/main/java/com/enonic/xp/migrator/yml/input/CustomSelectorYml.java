package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class CustomSelectorYml
    extends InputYml<String>
{
    public String service;

    public Map<String, String> params;

    public CustomSelectorYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        final InputTypeProperty serviceProperty = inputTypeConfig.getProperty( "service" );
        if ( serviceProperty != null )
        {
            service = serviceProperty.getValue();
        }

        final Set<InputTypeProperty> paramProperties = inputTypeConfig.getProperties( "param" );

        if ( paramProperties != null && !paramProperties.isEmpty() )
        {
            params = new LinkedHashMap<>();
            paramProperties.forEach( param -> params.put( param.getAttribute( "value" ), param.getValue() ) );
        }
    }
}
