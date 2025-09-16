package com.enonic.xp.migrator.yml;

import java.util.List;
import java.util.stream.Collectors;

import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.service.ServiceDescriptor;

public class ServiceDescriptorYml
{
    public List<String> allow;

    public ServiceDescriptorYml( final ServiceDescriptor descriptor )
    {
        allow = descriptor.getAllowedPrincipals().stream().map( PrincipalKey::toString ).collect( Collectors.toList() );
    }
}
