package com.gitlab.zachdeibert.conwaycastles.options;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@interface Option {
    String title();
    String group();
    Class<? extends AbstractOptionPanel> type();
}
