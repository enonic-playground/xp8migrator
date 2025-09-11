package com.enonic.xp.migrator.xml.parser;

import java.util.ArrayList;
import java.util.List;

import com.enonic.xp.migrator.xml.DomElement;
import com.enonic.xp.security.PrincipalKey;
import com.enonic.xp.security.PrincipalKeys;
import com.enonic.xp.service.ServiceDescriptor;

public final class XmlServiceDescriptorParser
    extends XmlModelParser<XmlServiceDescriptorParser>
{
    private ServiceDescriptor.Builder builder;

    public XmlServiceDescriptorParser builder( final ServiceDescriptor.Builder builder )
    {
        this.builder = builder;
        return this;
    }

    @Override
    protected void doParse( final DomElement root )
        throws Exception
    {
        assertTagName( root, "service" );

        final DomElement allowedPrincipals = root.getChild( "allow" );
        if ( allowedPrincipals != null )
        {
            final List<PrincipalKey> allowedPrincipalKeys = new ArrayList<>();
            final List<DomElement> allowedPrincipalList = allowedPrincipals.getChildren( "principal" );
            for ( DomElement allowedPrincipal : allowedPrincipalList )
            {
                allowedPrincipalKeys.add( PrincipalKey.from( allowedPrincipal.getValue().trim() ) );
            }
            this.builder.allowedPrincipals( PrincipalKeys.from( allowedPrincipalKeys ) );
        }
    }
}
