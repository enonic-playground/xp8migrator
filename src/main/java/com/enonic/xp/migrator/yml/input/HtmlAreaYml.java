package com.enonic.xp.migrator.yml.input;

import com.enonic.xp.form.Input;
import com.enonic.xp.inputtype.InputTypeConfig;

public class HtmlAreaYml
    extends InputYml
{
    public String exclude;

    public String include;

    public String allowHeadings;

    public HtmlAreaYml( final Input source )
    {
        super( source );

        final InputTypeConfig config = source.getInputTypeConfig();

        if ( config.getValue( "exclude" ) != null )
        {
            exclude = config.getValue( "exclude" );
        }

        if ( config.getValue( "include" ) != null )
        {
            include = config.getValue( "include" );
        }

        if ( config.getValue( "allowHeadings" ) != null )
        {
            allowHeadings = config.getValue( "allowHeadings" );
        }
    }
}
