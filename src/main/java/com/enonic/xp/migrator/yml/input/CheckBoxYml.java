package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;

public class CheckBoxYml
    extends InputYml<Boolean>
{

    public CheckBoxYml( final Input source )
    {
        super( source, Boolean.class );
        setConfig( source );
    }
}
