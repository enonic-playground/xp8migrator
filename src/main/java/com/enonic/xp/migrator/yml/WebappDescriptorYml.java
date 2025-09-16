package com.enonic.xp.migrator.yml;

import java.util.List;
import java.util.stream.Collectors;

import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.webapp.WebappDescriptor;

public class WebappDescriptorYml
{
    public List<String> apis;

    public WebappDescriptorYml( final WebappDescriptor descriptor )
    {
        if ( descriptor.getApiMounts() != null && !descriptor.getApiMounts().isEmpty() )
        {
            apis = descriptor.getApiMounts().stream().map( DescriptorKey::toString ).collect( Collectors.toList() );
        }
    }
}
