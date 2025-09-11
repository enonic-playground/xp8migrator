package com.enonic.xp.migrator.xml.parser;

import com.enonic.xp.migrator.xml.DomElement;
import com.enonic.xp.webapp.WebappDescriptor;

public final class XmlWebappDescriptorParser
    extends XmlModelParser<XmlWebappDescriptorParser>
{
    private static final String ROOT_TAG_NAME = "webapp";

    private static final String APIS_DESCRIPTOR_TAG_NAME = "apis";

    private WebappDescriptor.Builder descriptorBuilder;

    public XmlWebappDescriptorParser descriptorBuilder( final WebappDescriptor.Builder descriptorBuilder )
    {
        this.descriptorBuilder = descriptorBuilder;
        return this;
    }

    @Override
    protected void doParse( final DomElement root )
        throws Exception
    {
        assertTagName( root, ROOT_TAG_NAME );

        this.descriptorBuilder.applicationKey( this.currentApplication );

        final ApiMountDescriptorParser apiMountDescriptorParser =
            new ApiMountDescriptorParser( this.currentApplication, root.getChild( APIS_DESCRIPTOR_TAG_NAME ) );

        this.descriptorBuilder.apiMounts( apiMountDescriptorParser.parse() );
    }
}
