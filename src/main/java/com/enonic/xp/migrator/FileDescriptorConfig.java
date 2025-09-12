package com.enonic.xp.migrator;

import java.util.function.Supplier;

public record FileDescriptorConfig(String key, Supplier<? extends DescriptorMigrator> migratorSupplier)
    implements DescriptorConfig
{
}
