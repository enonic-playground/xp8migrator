package com.enonic.xp.migrator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

final class MigrationResult
{
    private final List<MigrationEntry> entries = new ArrayList<>();

    public void addEntry( final Path source, final boolean status )
    {
        entries.add( new MigrationEntry( source, status ) );
    }

    public List<MigrationEntry> getEntries()
    {
        return entries;
    }

    public record MigrationEntry(Path source, boolean migrated)
    {
    }
}
