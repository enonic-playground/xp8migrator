package com.enonic.xp.migrator.yml;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.enonic.xp.form.Form;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.content.ContentType;

import static com.google.common.base.Strings.emptyToNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentTypeYml
{
    public String superType;

    public DisplayNameYml displayName;

    @JsonProperty("abstract")
    public Boolean abstractValue;

    @JsonProperty("final")
    public Boolean finalValue;

    public Boolean allowChildContent;

    public LocalizedText label;

    public LocalizedText description;

    public List<String> allowChildContentType;

    public Form form;

    public ContentTypeYml( final ContentType descriptor )
    {
        superType = descriptor.getSuperType().toString();
        displayName =
            new DisplayNameYml( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey(), descriptor.getDisplayNameExpression() );
        label = LocalizeHelper.localizeProperty( descriptor.getDisplayNameLabel(), descriptor.getDisplayNameLabelI18nKey() );
        description = LocalizeHelper.localizeProperty( descriptor.getDescription(), descriptor.getDescriptionI18nKey() );
        abstractValue = descriptor.isAbstract();
        finalValue = descriptor.isFinal();
        allowChildContent = descriptor.allowChildContent();
        form = descriptor.getForm();

        final List<String> allowChildContentTypes = descriptor.getAllowChildContentType();
        if ( allowChildContentTypes != null && !allowChildContentTypes.isEmpty() )
        {
            allowChildContentType = allowChildContentTypes;
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DisplayNameYml
    {
        public String text;

        public String i18n;

        public String expression;

        public DisplayNameYml( final String text, final String i18n, final String expression )
        {
            this.text = emptyToNull( text );
            this.i18n = emptyToNull( i18n );
            this.expression = emptyToNull( expression );
        }
    }
}
