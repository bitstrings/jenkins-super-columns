package org.bitstrings.jenkins.plugin.supercolumns;

import static org.apache.commons.lang3.StringUtils.replace;

import java.util.regex.Pattern;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.Functions;
import hudson.model.Job;

public class NameColumn
    extends AbstractColumn
{
    private final String defaultFormat;
    private final Pattern nameRegex;
    private final String matchedFormat;

    @DataBoundConstructor
    public NameColumn(
            String defaultFormat,
            String nameRegex,
            String matchedFormat )
    {
        super();

        this.defaultFormat = defaultFormat;
        this.nameRegex =
            nameRegex == null
                ? null
                : Pattern.compile( nameRegex );
        this.matchedFormat = matchedFormat;
    }

    public NameColumn()
    {
        this( null, null, null );
    }

    @Override
    public String getText( Job<?, ?> job )
    {
        String fullDisplayName = Functions.htmlAttributeEscape( job.getFullDisplayName() );

        return
            ( nameRegex != null ) && nameRegex.matcher( job.getFullName() ).matches()
                ? replace( matchedFormat, "{}", fullDisplayName )
                : defaultFormat == null
                    ? fullDisplayName
                    : replace( defaultFormat, "{}", fullDisplayName );
    }

    public String getDefaultFormat()
    {
        return defaultFormat;
    }

    public Pattern getNameRegex()
    {
        return nameRegex;
    }

    public String getMatchedFormat()
    {
        return matchedFormat;
    }

    @Extension
    public static class DescriptorImpl
        extends AbstractColumnDescriptor
    {
        @Override
        public String getDisplayName()
        {
            return Messages.Column_Name_DisplayName();
        }
    }
}
