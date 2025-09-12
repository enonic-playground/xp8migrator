package com.enonic.xp.migrator.yml.input;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class OptionYml
{
    private final Map<String, String> attributes = new LinkedHashMap<>();

    @JsonAnySetter
    public void addAttribute( final String key, final String value )
    {
        attributes.put( key, value );
    }

    @JsonAnyGetter
    public Map<String, String> getAttributes()
    {
        return attributes;
    }

}
