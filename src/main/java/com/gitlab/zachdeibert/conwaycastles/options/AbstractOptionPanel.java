package com.gitlab.zachdeibert.conwaycastles.options;

import java.lang.reflect.Field;
import javax.swing.JPanel;

abstract class AbstractOptionPanel extends JPanel implements Comparable<AbstractOptionPanel> {
    private static final long serialVersionUID = 5613565882141581983L;
    protected Options obj;
    protected Field field;
    protected Option annotation;
    
    protected abstract void setOption();
    
    String getGroupName() {
        return annotation.group();
    }
    
    void setOption(final Options obj, final Field field, final Option annotation) {
        this.obj = obj;
        this.field = field;
        this.annotation = annotation;
        setOption();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (annotation == null) ? 0 : annotation.hashCode());
        result = prime * result + ( (field == null) ? 0 : field.hashCode());
        result = prime * result + ( (obj == null) ? 0 : obj.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if ( obj instanceof AbstractOptionPanel ) {
            return compareTo((AbstractOptionPanel) obj) == 0;
        } else {
            return super.equals(obj);
        }
    }
    
    @Override
    public int compareTo(final AbstractOptionPanel other) {
        final int group = annotation.group().compareTo(other.annotation.group());
        if ( group == 0 ) {
            final int title = annotation.title().compareTo(other.annotation.title());
            if ( title == 0 ) {
                return Integer.compare(hashCode(), other.hashCode());
            } else {
                return title;
            }
        } else {
            return group;
        }
    }
}
