package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;

public class GeoPointYml
    extends InputYml<String>
{
    public GeoPointYml( final Input source )
    {
        super( source, String.class );
    }
}
