package org.bitstrings.jenkins.plugin.supercolumns;

import java.util.function.Function;

import org.apache.commons.text.StrLookup;
import org.mvel2.MVEL;

public class ExprLookup
    extends StrLookup<String>
{
    private final Object ctx;
    private final Function<String, String> transform;

    public ExprLookup( Object ctx, Function<String, String> transform )
    {
        this.ctx = ctx;
        this.transform = transform;
    }

    @Override
    public String lookup( String key )
    {
        return transform.apply( MVEL.evalToString( key, ctx ) );
    }
}
