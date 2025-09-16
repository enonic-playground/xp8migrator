package com.enonic.xp.migrator.yml;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.enonic.xp.admin.tool.AdminToolDescriptor;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.descriptor.DescriptorKeys;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminToolDescriptorYml
{
    public LocalizedText displayName;

    public LocalizedText description;

    public List<String> allow;

    public List<String> apis;

    public List<String> interfaces;

    public AdminToolDescriptorYml( final AdminToolDescriptor descriptor )
    {
        displayName = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );

        final PrincipalKeys allowedPrincipals = descriptor.getAllowedPrincipals();
        if ( allowedPrincipals != null && allowedPrincipals.isNotEmpty() )
        {
            allow = allowedPrincipals.stream().map( PrincipalKey::toString ).collect( Collectors.toList() );
        }

        final DescriptorKeys apiMounts = descriptor.getApiMounts();
        if ( apiMounts != null && apiMounts.isNotEmpty() )
        {
            apis = apiMounts.stream().map( DescriptorKey::toString ).collect( Collectors.toList() );
        }

        final Set<String> interfaceList = descriptor.getInterfaces();
        if ( interfaceList != null && !interfaceList.isEmpty() )
        {
            interfaces = interfaceList.stream().toList();
        }
    }
}
