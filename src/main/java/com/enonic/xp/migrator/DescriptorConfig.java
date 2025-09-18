package com.enonic.xp.migrator;

import java.util.function.Function;

public interface DescriptorConfig
{
    String key();

    Function<MigrationParams, ? extends DescriptorMigrator> migratorSupplier();
}
