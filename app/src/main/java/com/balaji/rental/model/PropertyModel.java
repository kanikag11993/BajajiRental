package com.balaji.rental.model;

public class PropertyModel {

    String propertyTitle;
    String propertySubtitle;

    public PropertyModel(String propertyTitle, String propertySubtitle) {
        this.propertyTitle = propertyTitle;
        this.propertySubtitle = propertySubtitle;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public void setPropertyTitle(String propertyTitle) {
        this.propertyTitle = propertyTitle;
    }

    public String getPropertySubtitle() {
        return propertySubtitle;
    }

    public void setPropertySubtitle(String propertySubtitle) {
        this.propertySubtitle = propertySubtitle;
    }
}
