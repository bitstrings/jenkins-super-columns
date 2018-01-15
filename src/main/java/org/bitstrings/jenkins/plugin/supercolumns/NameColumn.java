package org.bitstrings.jenkins.plugin.supercolumns;

import hudson.Extension;

public class NameColumn
    extends AbstractColumn
{
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
