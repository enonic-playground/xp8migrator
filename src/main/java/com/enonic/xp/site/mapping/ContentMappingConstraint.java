package com.enonic.xp.site.mapping;

import java.util.Objects;

import com.enonic.xp.annotation.PublicApi;

@PublicApi
public final class ContentMappingConstraint
{
    private static final String SEPARATOR = ":";

    private final String id;

    private final String value;

    private ContentMappingConstraint( final String id, final String value )
    {
        this.id = id;
        this.value = value;
    }

    public static ContentMappingConstraint parse( final String expression )
    {
        int index = expression.indexOf( SEPARATOR );
        if ( index == -1 )
        {
            throw new IllegalArgumentException( "Invalid match expression: " + expression );
        }
        final String id = expression.substring( 0, index ).trim();
        final String value = expression.substring( index + 1 ).trim();

        if ( id.isBlank() )
        {
            throw new IllegalArgumentException( "Invalid match expression: " + expression );
        }
        return new ContentMappingConstraint( id, value );
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }
        final ContentMappingConstraint that = (ContentMappingConstraint) o;
        return Objects.equals( id, that.id ) && Objects.equals( value, that.value );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( id, value );
    }

    @Override
    public String toString()
    {
        return id + SEPARATOR + value;
    }
}
