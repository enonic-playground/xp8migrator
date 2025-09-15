package com.enonic.xp.migrator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.migrator.yml.ContentTypeYml;
import com.enonic.xp.schema.content.ContentType;
import com.enonic.xp.schema.content.ContentTypeName;
import com.enonic.xp.xml.XmlException;
import com.enonic.xp.xml.parser.XmlContentTypeParser;

public class ContentTypeMigrator
    extends DescriptorMigrator
{

    @Override
    public Object doMigrate( final ApplicationKey currentApplication, final Path source )
        throws IOException
    {
        try
        {
            final ContentType.Builder builder = ContentType.create();
            final XmlContentTypeParser parser = new XmlContentTypeParser();
            parser.currentApplication( currentApplication );
            parser.source( Files.readString( source ) );
            parser.builder( builder );
            parser.parse();
            builder.name( ContentTypeName.from( currentApplication, "_TEMP_" ) );

            final ContentType contentType = builder.build();
            return new ContentTypeYml( contentType );
        }
        catch ( Exception e )
        {
            throw new XmlException( e, "Could not parse content type [" + source + "]" );
        }
    }
}
