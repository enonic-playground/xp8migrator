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
    extends DescriptorMigrator
{
    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        final PartDescriptor.Builder builder = PartDescriptor.create();

        final XmlPartDescriptorParser parser = new XmlPartDescriptorParser();

        parser.builder( builder );
        parser.currentApplication( currentApplication );
        parser.source( Files.readString( source ) );
        parser.parse();
        builder.key( DescriptorKey.from( currentApplication, source.getFileName().toString() ) );

        final PartDescriptor descriptor = builder.build();
        return new PartDescriptorYml( descriptor );
    }
}
