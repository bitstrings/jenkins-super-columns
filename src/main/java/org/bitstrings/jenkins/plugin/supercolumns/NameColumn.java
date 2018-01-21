package org.bitstrings.jenkins.plugin.supercolumns;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
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
        return
            ( nameRegex != null ) && nameRegex.matcher( job.getFullName() ).matches()
                ? StringUtils.replace( matchedFormat, "{}", job.getFullDisplayName() )
                : defaultFormat == null
                    ? job.getFullDisplayName()
                    : StringUtils.replace( defaultFormat, "{}", job.getFullDisplayName() );
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
