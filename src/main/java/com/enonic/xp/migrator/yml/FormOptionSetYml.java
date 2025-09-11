package com.enonic.xp.migrator.yml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.FormOptionSet;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.schema.LocalizedText;

import static com.google.common.base.Strings.nullToEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormOptionSetYml
{
    public String type = "OptionSet";

    public String name;

    public boolean expanded;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    public Occurrences multiselection;

    public List<FormOptionSetOptionYml> options;

    public FormOptionSetYml( final FormOptionSet source )
    {
        name = source.getName();
        expanded = source.isExpanded();

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

        if ( source.getMultiselection() != null )
        {
            multiselection = source.getMultiselection();
        }

        options = new ArrayList<>();
        source.iterator().forEachRemaining( option -> options.add( new FormOptionSetOptionYml( option ) ) );
    }
}
