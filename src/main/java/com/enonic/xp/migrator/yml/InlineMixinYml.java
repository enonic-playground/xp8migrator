package com.enonic.xp.migrator.yml;

import com.enonic.xp.form.InlineMixin;

public class InlineMixinYml
    extends FormItemYml
{
    public String name;

    public InlineMixinYml( final InlineMixin source )
    {
        super( "InlineMixin" );

        name = source.getName();
    }
}
