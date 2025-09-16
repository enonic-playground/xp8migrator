package com.enonic.xp.migrator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.enonic.xp.app.ApplicationKey;

public final class MigrationExecutor
{
    private static final List<DescriptorConfig> DESCRIPTORS = new ArrayList<>();

    static
    {
        DESCRIPTORS.add( new DirDescriptorConfig( "site/parts", PartMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/content-types", ContentTypeMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/layouts", LayoutDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/mixins", MixinDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/pages", PageDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/x-data", XDataDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "site/macros", MacroDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "tasks", TaskDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "admin/tools", AdminToolDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "admin/widgets", WidgetDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "services", ServiceDescriptorMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "application.xml", ApplicationMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "site/site.xml", SiteMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "site/styles.xml", StyleDescriptorMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "idprovider/idprovider.xml", IdProviderDescriptorMigrator::new ) );
        DESCRIPTORS.add( new DirDescriptorConfig( "apis", ApiDescriptorMigrator::new ) );
        DESCRIPTORS.add( new FileDescriptorConfig( "webapp/webapp.xml", WebappDescriptorMigrator::new ) );
    }

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
