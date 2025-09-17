package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yml.CmsDescriptorYml;
import com.enonic.xp.migrator.yml.SiteDescriptorYml;
import com.enonic.xp.site.SiteDescriptor;
import com.enonic.xp.xml.parser.XmlSiteParser;

public class SiteMigrator
    extends DescriptorMigrator
{

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final SiteDescriptor.Builder builder = SiteDescriptor.create();

        final XmlSiteParser parser = new XmlSiteParser();
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.siteDescriptorBuilder( builder );
        parser.parse();

        builder.applicationKey( currentApplication );

        final SiteDescriptor siteDescriptor = builder.build();

        final Path cmsDir = source.getParent().getParent().resolve( "cms" );
        Files.createDirectories( cmsDir );

        final Path cmsPath = cmsDir.resolve( "cms.yml" );
        new CmsMigrator( siteDescriptor ).migrate( currentApplication, cmsPath );

        return new SiteDescriptorYml( siteDescriptor );

    }

    private static class CmsMigrator
        extends DescriptorMigrator
    {
        private final SiteDescriptor siteDescriptor;

        private CmsMigrator( final SiteDescriptor siteDescriptor )
        {
            this.siteDescriptor = siteDescriptor;
        }

        @Override
        public Object doMigrate( final ApplicationKey currentApplication, final Path source )
            throws IOException
        {
            return new CmsDescriptorYml( siteDescriptor );
        }
    }
}
