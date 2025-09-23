package com.enonic.xp.migrator.yml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Form;
import com.enonic.xp.region.PartDescriptor;
import com.enonic.xp.schema.LocalizedText;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public Form form;

    public PartDescriptorYml( final PartDescriptor source )
    {
        this.displayName = LocalizeHelper.localizeProperty( source.getDisplayName(), source.getDisplayNameI18nKey() );
        this.description = LocalizeHelper.localizeProperty( source.getDescription(), source.getDescriptionI18nKey() );
        this.form = source.getConfig();
    }
}
