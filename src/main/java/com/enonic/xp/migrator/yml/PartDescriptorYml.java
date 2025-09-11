package com.enonic.xp.migrator.yml;

import com.enonic.xp.form.Form;
import com.enonic.xp.region.PartDescriptor;
import com.enonic.xp.schema.LocalizedText;

public class PartDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public Form form;

    public PartDescriptorYml( final PartDescriptor source )
    {
        this.displayName = new LocalizedText( source.getDisplayName(), source.getDisplayNameI18nKey() );
        this.description = new LocalizedText( source.getDescription(), source.getDescriptionI18nKey() );
        this.form = source.getConfig();
    }
}
