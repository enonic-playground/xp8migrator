package com.enonic.xp.migrator.yml.mixin;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.FormItemType;
import com.enonic.xp.migrator.yml.FieldSetYml;
import com.enonic.xp.migrator.yml.FormItemSetYml;
import com.enonic.xp.migrator.yml.FormOptionSetOptionYml;
import com.enonic.xp.migrator.yml.FormOptionSetYml;

public class FormItemSerializer
    extends JsonSerializer<FormItem>
{
    @Override
    public void serialize( final FormItem formItem, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        final FormItemType type = formItem.getType();

        if ( type == FormItemType.FORM_ITEM_SET )
        {
            final FormItemSetYml target = new FormItemSetYml( formItem.toFormItemSet() );
            gen.writeObject( target );
        }
        else if ( type == FormItemType.FORM_OPTION_SET )
        {
            final FormOptionSetYml target = new FormOptionSetYml( formItem.toFormOptionSet() );
            gen.writeObject( target );
        }
        else if ( type == FormItemType.FORM_OPTION_SET_OPTION )
        {
            final FormOptionSetOptionYml target = new FormOptionSetOptionYml( formItem.toFormOptionSetOption() );
            gen.writeObject( target );
        }
        else if ( type == FormItemType.LAYOUT )
        {
            final FieldSetYml target = new FieldSetYml( formItem.toLayout() );
            gen.writeObject( target );
        }
        else if ( type == FormItemType.MIXIN_REFERENCE )
        {

        }
        else
        {
            if ( type.equals( FormItemType.INPUT ) )
            {
                final TextLine target = new TextLine( formItem.toInput() );
                gen.writeObject( target );
            }
        }
    }
}
