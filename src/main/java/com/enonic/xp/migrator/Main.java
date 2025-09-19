package com.enonic.xp.migrator;

import java.nio.file.Path;

public class Main
{
    public static void main( String[] args )
    {
        final Path sourceDir = Path.of( args[0] );
        final MigrationResult migrationResult = new MigrationExecutor( sourceDir ).migrate();

        migrationResult.getEntries().forEach(
            ( entry ) -> System.out.println( ( entry.migrated() ? "SUCCESS" : "FAILURE" ) + " " + entry.source() ) );
    }
}
