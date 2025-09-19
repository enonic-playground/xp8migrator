package com.enonic.xp.migrator.yml.input;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class ComboBoxYml
    extends InputYml<String>
{
    public List<OptionYml> options;

    public Map<String, Object> config;

    public ComboBoxYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();

        final Set<InputTypeProperty> values = inputTypeConfig.getProperties( "option" );

        if ( values != null )
        {
            options = new ArrayList<>();

            values.forEach( option -> {
                final OptionYml optionYml = new OptionYml();

                optionYml.addAttribute( "text", option.getValue() );
                optionYml.addAttribute( "value", option.getAttribute( "value" ) );
                option.getAttributes().forEach( ( key, value ) -> {
                    if ( !"value".equals( key ) )
                    {
                        optionYml.addAttribute( key, value );
                    }
                } );

                options.add( optionYml );
            } );
        }

        final Set<String> propertyNames =
            inputTypeConfig.getNames().stream().filter( name -> !name.equals( "option" ) ).collect( Collectors.toSet() );

        if ( !propertyNames.isEmpty() )
        {
            config = new LinkedHashMap<>();

            propertyNames.forEach( propertyName -> {
                final Set<InputTypeProperty> properties = inputTypeConfig.getProperties( propertyName );
                if ( properties.size() == 1 )
                {
                    config.put( propertyName, properties.iterator().next().getValue() );
                }
                else
                {
                    config.put( propertyName, properties.stream().map( InputTypeProperty::getValue ).collect( Collectors.toSet() ) );
                }
            } );
        }
    }

}
