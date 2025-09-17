package com.enonic.xp.migrator.yml.mixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.enonic.xp.form.FieldSet;
import com.enonic.xp.form.FormItem;
import com.enonic.xp.form.FormItemType;
import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeName;
import com.enonic.xp.migrator.yml.FieldSetYml;
import com.enonic.xp.migrator.yml.FormItemSetYml;
import com.enonic.xp.migrator.yml.FormOptionSetOptionYml;
import com.enonic.xp.migrator.yml.FormOptionSetYml;
import com.enonic.xp.migrator.yml.InlineMixinYml;
import com.enonic.xp.migrator.yml.input.AttachmentUploaderYml;
import com.enonic.xp.migrator.yml.input.CheckBoxYml;
import com.enonic.xp.migrator.yml.input.ComboBoxYml;
import com.enonic.xp.migrator.yml.input.ContentSelectorYml;
import com.enonic.xp.migrator.yml.input.ContentTypeFilterYml;
import com.enonic.xp.migrator.yml.input.CustomSelectorYml;
import com.enonic.xp.migrator.yml.input.DateTimeYml;
import com.enonic.xp.migrator.yml.input.DateYml;
import com.enonic.xp.migrator.yml.input.DoubleYml;
import com.enonic.xp.migrator.yml.input.GeoPointYml;
import com.enonic.xp.migrator.yml.input.HtmlAreaYml;
import com.enonic.xp.migrator.yml.input.ImageSelectorYml;
import com.enonic.xp.migrator.yml.input.LongYml;
import com.enonic.xp.migrator.yml.input.MediaSelectorYml;
import com.enonic.xp.migrator.yml.input.RadioButtonYml;
import com.enonic.xp.migrator.yml.input.TagYml;
import com.enonic.xp.migrator.yml.input.TextAreaYml;
import com.enonic.xp.migrator.yml.input.TextLineYml;
import com.enonic.xp.migrator.yml.input.TimeYml;

public class FormItemSerializer
    extends JsonSerializer<FormItem>
{
    private static final Map<InputTypeName, Function<Input, Object>> CONVERTERS = new HashMap<>();

    static
    {
        CONVERTERS.put( InputTypeName.TEXT_LINE, TextLineYml::new );
        CONVERTERS.put( InputTypeName.CHECK_BOX, CheckBoxYml::new );
        CONVERTERS.put( InputTypeName.COMBO_BOX, ComboBoxYml::new );
        CONVERTERS.put( InputTypeName.CONTENT_TYPE_FILTER, ContentTypeFilterYml::new );
        CONVERTERS.put( InputTypeName.CUSTOM_SELECTOR, CustomSelectorYml::new );
        CONVERTERS.put( InputTypeName.DATE_TIME, DateTimeYml::new );
        CONVERTERS.put( InputTypeName.DATE, DateYml::new );
        CONVERTERS.put( InputTypeName.ATTACHMENT_UPLOADER, AttachmentUploaderYml::new );
        CONVERTERS.put( InputTypeName.CONTENT_SELECTOR, ContentSelectorYml::new );
        CONVERTERS.put( InputTypeName.DOUBLE, DoubleYml::new );
        CONVERTERS.put( InputTypeName.GEO_POINT, GeoPointYml::new );
        CONVERTERS.put( InputTypeName.HTML_AREA, HtmlAreaYml::new );
        CONVERTERS.put( InputTypeName.IMAGE_SELECTOR, ImageSelectorYml::new );
        CONVERTERS.put( InputTypeName.LONG, LongYml::new );
        CONVERTERS.put( InputTypeName.MEDIA_SELECTOR, MediaSelectorYml::new );
        CONVERTERS.put( InputTypeName.RADIO_BUTTON, RadioButtonYml::new );
        CONVERTERS.put( InputTypeName.TAG, TagYml::new );
        CONVERTERS.put( InputTypeName.TEXT_AREA, TextAreaYml::new );
        CONVERTERS.put( InputTypeName.TIME, TimeYml::new );
    }

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
            case MIXIN_REFERENCE -> gen.writeObject( new InlineMixinYml( formItem.toInlineMixin() ) );
            case INPUT -> gen.writeObject( convertInputToYml( formItem.toInput() ) );
            case LAYOUT ->
            {
                if ( !( formItem instanceof FieldSet ) )
                {
                    throw new IllegalArgumentException(
                        "This FormItem [" + formItem.getName() + "] is not a FieldSet: " + formItem.getClass().getSimpleName() );
                }
                gen.writeObject( new FieldSetYml( (FieldSet) formItem ) );
            }
            default -> throw new IllegalArgumentException( "Unsupported FormItemType: " + type );
        }
    }

    private Object convertInputToYml( final Input input )
    {
        final Function<Input, Object> converter = CONVERTERS.get( input.getInputType() );
        if ( converter == null )
        {
            throw new IllegalArgumentException( "Unsupported InputType: " + input.getInputType() );
        }
        return converter.apply( input );
    }
}
