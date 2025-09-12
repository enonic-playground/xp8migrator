package com.enonic.xp.migrator.yml;

import com.enonic.xp.app.ApplicationDescriptor;

public class ApplicationDescriptorYml
{
    public String description;

    public ApplicationDescriptorYml( final ApplicationDescriptor source )
    {
        this.description = source.getDescription();
    }
}
