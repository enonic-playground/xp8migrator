package com.enonic.xp.migrator.yml.input;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class RadioButtonYml
    extends InputYml<String>
{
    public List<OptionYml> options;

    public RadioButtonYml( final Input source )
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
    }
}
