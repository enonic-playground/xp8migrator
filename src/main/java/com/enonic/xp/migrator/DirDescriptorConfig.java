package com.enonic.xp.migrator;

import java.util.function.Function;

public record DirDescriptorConfig(String key, Function<MigrationParams, ? extends DescriptorMigrator> migratorSupplier)
    implements DescriptorConfig
{
}

