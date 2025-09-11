package com.enonic.xp.migrator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import com.enonic.xp.form.Form;
import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.migrator.yml.mixin.FormItemSerializer;
import com.enonic.xp.migrator.yml.mixin.FormSerializer;
import com.enonic.xp.migrator.yml.mixin.LocalizedTextSerializer;
import com.enonic.xp.migrator.yml.mixin.OccurrencesSerializer;
import com.enonic.xp.schema.LocalizedText;

public abstract class MigratorBase
{
    private static final ObjectMapper MAPPER =
        new ObjectMapper( new YAMLFactory().disable( YAMLGenerator.Feature.WRITE_DOC_START_MARKER ) );

    static
    {
        final SimpleModule module = new SimpleModule();

        module.addSerializer( Form.class, new FormSerializer() );
        module.addSerializer( FormItem.class, new FormItemSerializer() );
        module.addSerializer( LocalizedText.class, new LocalizedTextSerializer() );
        module.addSerializer( Occurrences.class, new OccurrencesSerializer() );

        MAPPER.registerModule( module );
    }

    public void migrate( final Path source )
    {
        try
        {
            final Object yaml = doMigrate( source );
            MAPPER.writeValue( resolveFile( source ), yaml );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( e );
        }
    }

    public abstract Object doMigrate( final Path source )
        throws IOException;

    private File resolveFile( final Path source )
    {
        return source.getParent().resolve( source.getFileName().toString().replace( ".xml", ".yml" ) ).toFile();
    }
}
