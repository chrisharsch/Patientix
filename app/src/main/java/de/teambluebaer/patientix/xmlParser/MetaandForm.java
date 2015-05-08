package de.teambluebaer.patientix.xmlParser;

import org.w3c.dom.NodeList;

/**
 * Created by Simon on 07.05.2015.
 *
 * Singleton, represents a MetaData and Form combination
 */
public final class MetaandForm {
    private static MetaandForm ourInstance = new MetaandForm();

    public static MetaandForm getInstance() {
        return ourInstance;
    }

    private MetaData meta;
    private Form form;


    /**
     * Private Constructor
     */
    private MetaandForm() {

    }

    /**
     * set MetaData of a MetaandForm Objekt
     * @param meta MetaData you want to add
     */
    protected void setMeta(MetaData meta){
        this.meta = meta;
    }

    /**
     *
     * @param form
     */
    protected void setForm(Form form){
        this.form = form;
    }

    public Form getForm(){
        return this.form;
    }

    public MetaData getMeta(){
        return this.meta;
    }


}


