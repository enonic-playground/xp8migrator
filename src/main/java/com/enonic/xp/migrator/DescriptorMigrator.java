package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import com.enonic.xp.app.ApplicationKey;
import com.enonic.xp.form.Form;
import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.Occurrences;
import com.enonic.xp.migrator.yml.mixin.FormItemSerializer;
import com.enonic.xp.migrator.yml.mixin.FormSerializer;
import com.enonic.xp.migrator.yml.mixin.LocalizedTextSerializer;
import com.enonic.xp.migrator.yml.mixin.OccurrencesSerializer;
import com.enonic.xp.schema.LocalizedText;

public abstract class DescriptorMigrator
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

    protected final ApplicationKey currentApplication;

    protected final Path resourcesDir;

    protected final Path source;

    protected DescriptorMigrator( final MigrationParams params )
    {
        this.currentApplication = params.currentApplication();
        this.resourcesDir = params.resourcesDir();
        this.source = params.source();
    }

    public void migrate()
    {
        try
        {
            final Object yaml = doMigrate( currentApplication, source );
            final Path targetPath = resolveMigratedFilePath( source );
            MAPPER.writeValue( targetPath.toFile(), yaml );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( e );
        }
    }

    public abstract Object doMigrate( ApplicationKey currentApplication, final Path source )
        throws IOException;

    public abstract Path resolveMigratedFilePath( final Path sourcePath )
        throws IOException;

    protected final Path resolveFileInDirectoryWithSameName( final String... pathSegments )
        throws IOException
    {
        Path base = resourcesDir;
        for ( String pathSegment : pathSegments )
        {
            base = base.resolve( pathSegment );
        }

        final String targetName = source.getParent().getFileName().toString();
        base = base.resolve( targetName );

        Files.createDirectories( base );

        return base.resolve( targetName + ".yml" );
    }

    protected final Path moveTo( final String... pathSegments )
        throws IOException
    {
        Path base = resourcesDir;
        for ( String pathSegment : pathSegments )
        {
            base = base.resolve( pathSegment );
        }

        Files.createDirectories( base );

        return base.resolve( source.getFileName().toString().replace( ".xml", ".yml" ) );
    }

    protected final Path changeExtensionToYml()
    {
        return source.getParent().resolve( source.getFileName().toString().replace( ".xml", ".yml" ) );
    }
}
