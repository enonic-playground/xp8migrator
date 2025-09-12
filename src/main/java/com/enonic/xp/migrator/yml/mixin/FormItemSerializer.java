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
import com.enonic.xp.migrator.yml.InlineMixinYml;

public class FormItemSerializer
    extends JsonSerializer<FormItem>
{
    @Override
    public void serialize( final FormItem formItem, final JsonGenerator gen, final SerializerProvider serializers )
        throws IOException
    {
        final FormItemType type = formItem.getType();

        switch ( type )
        {
            case FORM_ITEM_SET -> gen.writeObject( new FormItemSetYml( formItem.toFormItemSet() ) );
            case FORM_OPTION_SET -> gen.writeObject( new FormOptionSetYml( formItem.toFormOptionSet() ) );
            case FORM_OPTION_SET_OPTION -> gen.writeObject( new FormOptionSetOptionYml( formItem.toFormOptionSetOption() ) );
            case LAYOUT -> gen.writeObject( new FieldSetYml( formItem.toLayout() ) );
            case MIXIN_REFERENCE -> gen.writeObject( new InlineMixinYml( formItem.toInlineMixin() ) );
            case INPUT -> gen.writeObject( new TextLine( formItem.toInput() ) );
            default -> throw new IllegalArgumentException( "Unsupported FormItemType: " + type );
        }
    }
}
