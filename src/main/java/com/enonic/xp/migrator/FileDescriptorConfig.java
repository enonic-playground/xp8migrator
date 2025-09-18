package com.enonic.xp.migrator;

import java.util.function.Function;

public record FileDescriptorConfig(String key, Function<MigrationParams, ? extends DescriptorMigrator> migratorSupplier)
    implements DescriptorConfig
{
}
