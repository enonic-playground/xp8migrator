package com.enonic.xp.migrator.yml.input;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.inputtype.InputTypeProperty;

public class AttachmentUploaderYml
    extends InputYml<String>
{
    public Map<String, Object> config;

    public AttachmentUploaderYml( final Input source )
    {
        super( source, String.class );

        final InputTypeConfig inputTypeConfig = source.getInputTypeConfig();
        final Iterator<InputTypeProperty> iterator = inputTypeConfig.iterator();

        if ( iterator.hasNext() )
        {
            config = new LinkedHashMap<>();
            final InputTypeProperty inputTypeProperty = iterator.next();
            config.put( inputTypeProperty.getName(), inputTypeProperty.getValue() );
        }
    }
}
