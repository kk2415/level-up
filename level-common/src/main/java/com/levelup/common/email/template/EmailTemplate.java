package com.levelup.common.email.template;

import java.util.Map;

public abstract class EmailTemplate {

    private final String viewPath;
    private final Map<String, Object> model;

    public EmailTemplate(String viewPath, Map<String, Object> model) {
        this.viewPath = viewPath;
        this.model = model;
    }

    public String getViewPath() {
        return viewPath;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
