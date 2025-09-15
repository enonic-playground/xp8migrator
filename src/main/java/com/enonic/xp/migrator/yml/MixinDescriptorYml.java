package com.enonic.xp.migrator.yml;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.form.Form;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.mixin.Mixin;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MixinDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public Form form;

    public MixinDescriptorYml( final Mixin descriptor )
    {
        displayName = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        form = descriptor.getForm();
    }
}
