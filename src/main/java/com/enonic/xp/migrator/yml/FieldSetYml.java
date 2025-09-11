package com.enonic.xp.migrator.yml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.FieldSet;
import com.enonic.xp.form.FormItem;
import com.enonic.xp.schema.LocalizedText;

import static com.google.common.base.Strings.nullToEmpty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldSetYml
{
    public String type = "FieldSet";

    public LocalizedText label;

    public List<FormItem> items;

    public FieldSetYml( final FieldSet source )
    {
        if ( !nullToEmpty( source.getLabel() ).isEmpty() || !nullToEmpty( source.getLabelI18nKey() ).isEmpty() )
        {
            label = new LocalizedText( source.getLabel(), source.getLabelI18nKey() );
        }

        items = new ArrayList<>();
        source.iterator().forEachRemaining( items::add );
    }
}
