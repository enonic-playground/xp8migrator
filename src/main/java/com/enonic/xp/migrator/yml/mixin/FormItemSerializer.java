package com.enonic.xp.migrator.yml.mixin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.FormItemType;
import com.enonic.xp.form.Input;

public class FormItemSerializer
    extends JsonSerializer<FormItem>
{
    @Override
    public void serialize( final FormItem formItem, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        final ObjectMapper mapper = (ObjectMapper) gen.getCodec();

        if ( formItem.getType().equals( FormItemType.INPUT ) )
        {
            final Input source = formItem.toInput();

            final TextLine target = new TextLine();
            target.type = source.getInputType().toString();
            target.name = source.getName();
            target.label = source.getLabel();

            gen.writeObject( target );

            return;
        }

        throw new IllegalArgumentException( "Unknown form item type: " + formItem.getType().toString() );
    }
}
