package org.bitstrings.jenkins.plugin.supercolumns;

import hudson.model.Job;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

public abstract class AbstractColumn
    extends ListViewColumn
{
    public abstract String getText( Job<?, ?> job );

    public abstract static class AbstractColumnDescriptor
        extends ListViewColumnDescriptor
    {
    }
}
