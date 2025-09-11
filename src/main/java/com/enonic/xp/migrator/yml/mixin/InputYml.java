package com.enonic.xp.migrator.yml.mixin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.enonic.xp.form.Input;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.inputtype.InputTypeDefault;
import com.enonic.xp.schema.LocalizedText;

import static com.google.common.base.Strings.nullToEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InputYml
{
    public String type;

    public String name;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    @JsonProperty("default")
    public Object defaultValue;

    public InputYml( final Input source )
    {
        type = source.getInputType().toString();
        name = source.getName();

        if ( !nullToEmpty( source.getLabel() ).isEmpty() || !nullToEmpty( source.getLabelI18nKey() ).isEmpty() )
        {
            label = new LocalizedText( source.getLabel(), source.getLabelI18nKey() );
        }

        if ( !nullToEmpty( source.getHelpText() ).isEmpty() || !nullToEmpty( source.getHelpTextI18nKey() ).isEmpty() )
        {
            helpText = new LocalizedText( source.getHelpText(), source.getHelpTextI18nKey() );
        }

        if ( source.getOccurrences() != null )
        {
            occurrences = source.getOccurrences();
        }

        final InputTypeDefault inputDefaultValue = source.getDefaultValue();
        if ( inputDefaultValue != null )
        {
            defaultValue = inputDefaultValue.getValue( "default" );
        }
    }
}
