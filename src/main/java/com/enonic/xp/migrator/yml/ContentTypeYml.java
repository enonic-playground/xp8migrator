package com.enonic.xp.migrator.yml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import com.enonic.xp.form.Form;
import com.enonic.xp.inputtype.InputTypeConfig;
import com.enonic.xp.schema.LocalizedText;
import com.enonic.xp.schema.content.ContentType;

@JsonPropertyOrder({"superType", "abstract", "final", "allowChildContent", "displayName", "description", "form", "config"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentTypeYml
{
    public String superType;

    @JsonProperty("abstract")
    public Boolean abstractValue;

    @JsonProperty("final")
    public Boolean finalValue;

    public Boolean allowChildContent;

    public LocalizedText displayName;

    public LocalizedText description;

    public List<String> allowChildContentType;

    public Map<String, Object> config = new LinkedHashMap<>();

    public Form form;

    public ContentTypeYml( final ContentType descriptor )
    {
        superType = descriptor.getSuperType().toString();
        displayName = LocalizeHelper.localizeProperty( descriptor.getDisplayName(), descriptor.getDisplayNameI18nKey() );
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

        final LocalizedText displayNamePlaceholder =
            LocalizeHelper.localizeProperty( descriptor.getDisplayNameLabel(), descriptor.getDisplayNameLabelI18nKey() );

        if ( displayNamePlaceholder != null )
        {
            config.put( "displayNamePlaceholder", displayNamePlaceholder );
        }

        if ( descriptor.getDisplayNameExpression() != null )
        {
            config.put( "displayNameExpression", descriptor.getDisplayNameExpression() );
        }

        final InputTypeConfig schemaConfig = descriptor.getSchemaConfig();
        if ( schemaConfig != null )
        {
            schemaConfig.iterator().forEachRemaining( property -> config.put( property.getName(), property.getValue() ) );
        }
    }
}
