package com.enonic.xp.migrator;

import java.nio.file.Path;

import com.enonic.xp.app.ApplicationKey;

public record MigrationParams(ApplicationKey currentApplication, Path resourcesDir, Path source)
{
}
