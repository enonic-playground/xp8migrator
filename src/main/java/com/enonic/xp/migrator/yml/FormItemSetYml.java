package com.enonic.xp.migrator.yml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.FormItemSet;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.schema.LocalizedText;

import static com.google.common.base.Strings.nullToEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormItemSetYml
{
    public String type = "ItemSet";

    public String name;

    public LocalizedText label;

    public LocalizedText helpText;

    public Occurrences occurrences;

    public List<FormItem> items;

    public FormItemSetYml( final FormItemSet source )
    {
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

        items = new ArrayList<>();
        source.iterator().forEachRemaining( items::add );
    }
}
