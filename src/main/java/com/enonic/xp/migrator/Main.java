package com.enonic.xp.migrator;

import java.nio.file.Path;
import java.util.Objects;

public class Main
{
    public static void main( String[] args )
    {
        final Path sourceDir = Path.of( Objects.requireNonNullElse( args.length > 0 ? args[0] : null, "src/main/resources/myapp" ) );
        final MigrationResult migrationResult = new MigrationExecutor( sourceDir ).migrate();

        migrationResult.getEntries().forEach(
            ( entry ) -> System.out.println( ( entry.migrated() ? "SUCCESS" : "FAILURE" ) + " " + entry.source() ) );
    }
}
