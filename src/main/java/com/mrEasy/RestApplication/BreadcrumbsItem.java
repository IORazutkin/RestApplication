package com.mrEasy.RestApplication;

public class BreadcrumbsItem {
    private String text;
    private Boolean disabled;
    private String href;

    public BreadcrumbsItem(String text, Boolean disabled, String href) {
        this.text = text;
        this.disabled = disabled;
        this.href = href;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public boolean equals(Object obj) {
        return text.equals(((BreadcrumbsItem)obj).text);
    }
}
