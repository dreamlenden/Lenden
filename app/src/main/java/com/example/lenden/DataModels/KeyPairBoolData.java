package com.example.lenden.DataModels;

public class KeyPairBoolData {
    private long id;
    private String name;
    private boolean isSelected;
    private ContactModel contact;

    public ContactModel getContact() {
        return contact;
    }

    public void setContact(ContactModel contact) {
        this.contact = contact;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * @param isSelected the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}