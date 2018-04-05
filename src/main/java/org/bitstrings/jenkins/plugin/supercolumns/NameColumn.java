package org.bitstrings.jenkins.plugin.supercolumns;

import java.util.regex.Pattern;

import org.apache.commons.text.StrSubstitutor;
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
    private final String entryKey;

    @DataBoundConstructor
    public NameColumn(
            String defaultFormat,
            String nameRegex,
            String matchedFormat,
            String entryKey )
    {
        super();

        this.defaultFormat = defaultFormat;
        this.nameRegex =
            nameRegex == null
                ? null
                : Pattern.compile( nameRegex );
        this.matchedFormat = matchedFormat;
        this.entryKey = entryKey;
    }

    public NameColumn()
    {
        this( null, null, null, null );
    }

    @Override
    public String getText( Job<?, ?> job )
    {
        StrSubstitutor substitutor =
            new StrSubstitutor(
                new ExprLookup( job, str -> Functions.htmlAttributeEscape( str ) ) );

        return
            ( nameRegex != null ) && nameRegex.matcher( job.getFullName() ).matches()
                ? substitutor.replace( matchedFormat )
                : defaultFormat == null
                    ? job.getFullDisplayName()
                    : substitutor.replace( defaultFormat );
    }

    public String getEntryKey( Job<?, ?> job )
    {
        return
            entryKey == null
                ? Functions.htmlAttributeEscape( job.getName() )
                : new StrSubstitutor( new ExprLookup( job, str -> Functions.htmlAttributeEscape( str ) ) )
                    .replace( entryKey );
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

    public String getEntryKey()
    {
        return entryKey;
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
