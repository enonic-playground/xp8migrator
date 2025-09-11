package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.descriptor.DescriptorKey;
import com.enonic.xp.migrator.xml.parser.XmlPartDescriptorParser;
import com.enonic.xp.migrator.yml.PartDescriptorYml;
import com.enonic.xp.region.PartDescriptor;

public class PartMigrator
    extends MigratorBase
{
    @Override
    public Object doMigrate( final Path source )
        throws IOException
    {
        final PartDescriptor.Builder builder = PartDescriptor.create();

        final XmlPartDescriptorParser parser = new XmlPartDescriptorParser();

        final ApplicationKey myapp = ApplicationKey.from( "myapp" );

        parser.builder( builder );
        parser.currentApplication( myapp );
        parser.source( Files.readString( source ) );
        parser.parse();
        builder.key( DescriptorKey.from( myapp, source.getFileName().toString() ) );

        final PartDescriptor descriptor = builder.build();
        return new PartDescriptorYml( descriptor );
    }
}
