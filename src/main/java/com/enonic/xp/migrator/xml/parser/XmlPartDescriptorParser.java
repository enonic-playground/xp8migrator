package com.enonic.xp.migrator.xml.parser;

import com.enonic.xp.migrator.xml.DomElement;
import com.enonic.xp.region.PartDescriptor;

public final class XmlPartDescriptorParser
    extends XmlModelParser<XmlPartDescriptorParser>
{
    private static final XmlInputTypeConfigMapper CONFIG_MAPPER = new XmlInputTypeConfigMapper();

    private PartDescriptor.Builder builder;

    public XmlPartDescriptorParser builder( final PartDescriptor.Builder builder )
    {
        this.builder = builder;
        return this;
    }

    @Override
    protected void doParse( final DomElement root )
        throws Exception
    {
        assertTagName( root, "part" );
        this.builder.displayName( root.getChildValueTrimmed( "display-name" ) );
        this.builder.displayNameI18nKey(
            root.getChild( "display-name" ) != null ? root.getChild( "display-name" ).getAttribute( "i18n" ) : null );

        this.builder.description( root.getChildValue( "description" ) );
        this.builder.descriptionI18nKey(
            root.getChild( "description" ) != null ? root.getChild( "description" ).getAttribute( "i18n" ) : null );


        final XmlFormMapper mapper = new XmlFormMapper( this.currentApplication );
        this.builder.config( mapper.buildForm( root.getChild( "form" ) ) );
        this.builder.schemaConfig( CONFIG_MAPPER.build( root.getChild( "config" ) ) );
    }
}
