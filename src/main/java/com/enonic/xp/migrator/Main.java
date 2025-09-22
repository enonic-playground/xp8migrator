package com.enonic.xp.migrator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.enonic.xp.app.ApplicationKey;

public class Main
{
    public static void main( String[] args )
    {
        if ( args.length == 0 )
        {
            System.err.println( "Missed command line argument \"projectPath\". " );
            System.exit( 1 );
        }

        final Path projectPath = Paths.get( args[0] );

        final ApplicationKey currentApplication = args.length > 1 ? ApplicationKey.from( args[1] ) : resolveApplicationKey( projectPath );

        System.out.printf( "Start the migration of all descriptors for the project: %s%n", projectPath );
        System.out.printf( "Resolved applicationKey: %s%n", currentApplication );

        final MigrationResult migrationResult = new MigrationExecutor( projectPath, currentApplication ).migrate();

        migrationResult.getEntries().forEach(
            ( entry ) -> System.out.println( ( entry.migrated() ? "SUCCESS" : "FAILURE" ) + " " + entry.source() ) );

        System.out.println( "Migration completed successfully." );
    }

    private static ApplicationKey resolveApplicationKey( final Path projectPath )
    {
        final Path gradlePropertiesPath = projectPath.resolve( "gradle.properties" );
        if ( Files.notExists( gradlePropertiesPath ) )
        {
            System.err.println( "File gradle.properties not found." );
            System.exit( 1 );
        }

        final Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream( gradlePropertiesPath.toFile() ))
        {
            props.load( fis );

            final String appName = props.getProperty( "appName" );
            if ( appName == null || appName.isEmpty() )
            {
                System.err.println( "Missed the \"appName\" property in the gradle.properties file." );
                System.exit( 1 );
            }

            return ApplicationKey.from( appName );
        }
        catch ( IOException e )
        {
            throw new UncheckedIOException( "Failed to read the gradle.properties file", e );
        }
    }
}
