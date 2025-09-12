package com.enonic.xp.migrator;

import java.util.function.Supplier;

public interface DescriptorConfig
{
    String key();

    Supplier<? extends DescriptorMigrator> migratorSupplier();
}
