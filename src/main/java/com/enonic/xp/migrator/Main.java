package com.enonic.xp.migrator;

import java.nio.file.Path;

public class Main
{
    public static void main( String[] args )
    {
        final Path sourceDir = Path.of( "src/main/resources" );

        new ApplicationMigrator().migrate( sourceDir.resolve( "application.xml" ) );
        new PartMigrator().migrate( sourceDir.resolve( "part.xml" ) );
    }
}
