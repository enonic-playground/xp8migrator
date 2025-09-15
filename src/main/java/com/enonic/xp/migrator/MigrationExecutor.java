package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.enonic.xp.app.ApplicationKey;

public final class MigrationExecutor
{
    private static final Supplier<DescriptorMigrator> FAKE_MIGRATOR = () -> new DescriptorMigrator()
    {
        @Override
        public Object doMigrate( final ApplicationKey currentApplication, final Path source )
            throws IOException
        {
            throw new UnsupportedOperationException( "Not supported yet" );
        }
    };

    private static final List<DescriptorConfig> DESCRIPTORS = List.of( new DirDescriptorConfig( "site/parts", PartMigrator::new ),
                                                                       new DirDescriptorConfig( "site/content-types",
                                                                                                ContentTypeMigrator::new ),
                                                                       new DirDescriptorConfig( "site/layouts", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "site/mixins", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "site/pages", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "site/x-data", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "site/macros",
                                                                                                MacroDescriptorMigrator::new ),
                                                                       new DirDescriptorConfig( "tasks", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "admin/tools", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "admin/widgets", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "services", FAKE_MIGRATOR ),
                                                                       new DirDescriptorConfig( "apis", FAKE_MIGRATOR ),
                                                                       new FileDescriptorConfig( "application.xml",
                                                                                                 ApplicationMigrator::new ),
                                                                       new FileDescriptorConfig( "site/site.xml", SiteMigrator::new ),
                                                                       new FileDescriptorConfig( "site/styles.xml",
                                                                                                 StyleDescriptorMigrator::new ),
                                                                       new FileDescriptorConfig( "idprovider/idprovider.xml",
                                                                                                 FAKE_MIGRATOR ),
                                                                       new FileDescriptorConfig( "webapp/webapp.xml", FAKE_MIGRATOR ) );

    private final Path projectPath;

    public MigrationExecutor( final Path projectPath )
    {
        this.projectPath = projectPath;
    }

    public MigrationResult migrate()
    {
        final MigrationResult result = new MigrationResult();

        final Path resourcesDir = projectPath.resolve( "src/main/resources" );

        final ApplicationKey currentApplication = ApplicationKey.from( "myapp" );

        DESCRIPTORS.forEach( descriptorConfig -> {
            final Path base = resourcesDir.resolve( descriptorConfig.key() );

            if ( !Files.exists( base ) )
            {
                return;
            }

            final DescriptorMigrator migrator = descriptorConfig.migratorSupplier().get();

            try
            {
                if ( descriptorConfig instanceof DirDescriptorConfig )
                {
                    try (Stream<Path> paths = Files.walk( base ))
                    {
                        paths.filter( Files::isRegularFile ).filter( p -> {
                            final Path parent = p.getParent();
                            final Path expected = parent.resolve( parent.getFileName().toString() + ".xml" );
                            return p.equals( expected );
                        } ).forEach( descriptor -> {
                            doMigrate( result, currentApplication, migrator, descriptor );
                        } );
                    }
                }
                else
                {
                    doMigrate( result, currentApplication, migrator, base );
                }
            }
            catch ( IOException e )
            {
                throw new UncheckedIOException( e );
            }
        } );

        return result;
    }

    private void doMigrate( final MigrationResult result, final ApplicationKey currentApplication, final DescriptorMigrator migrator,
                            final Path descriptor )
    {
        try
        {
            migrator.migrate( currentApplication, descriptor );
            result.addEntry( descriptor, true );
        }
        catch ( Exception e )
        {
            result.addEntry( descriptor, false );
        }
    }
}
