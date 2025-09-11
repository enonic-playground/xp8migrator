package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationDescriptor;
import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.xml.parser.XmlApplicationParser;
import com.enonic.xp.migrator.yml.ApplicationDescriptorYml;

public class ApplicationMigrator
    extends MigratorBase
{
    @Override
    public Object doMigrate( final Path source )
        throws IOException
    {
        final ApplicationDescriptor.Builder appDescriptorBuilder = ApplicationDescriptor.create();

        final XmlApplicationParser parser = new XmlApplicationParser();

        parser.appDescriptorBuilder( appDescriptorBuilder );
        parser.currentApplication( ApplicationKey.from( "myapp" ) );
        parser.source( Files.readString( source ) );
        parser.parse();

        final ApplicationDescriptor descriptor = appDescriptorBuilder.build();

        return new ApplicationDescriptorYml( descriptor );
    }
}
