package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 07.05.2015.
 *
 * Singleton, represents a <code>MetaData</code> and <code>Form</code> combination
 * @see MetaData
 * @see Form
 *
 */
public final class MetaandForm {
    private static MetaandForm ourInstance = new MetaandForm();

    /**
     * give access to the Singleton Instance
     * @return a Instance of <code>MetaandForm</code>
     */
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
     * set MetaData of a <code>MetaAnfForm</code> Object
     * @param meta <code>MetaData</code> you want to add
     */
    protected void setMeta(MetaData meta){
        this.meta = meta;
    }

    /**
     * set Form of a <code>MetaAnfForm</code> Object
     * @param form <code>Form</code> you want to add
     */
    protected void setForm(Form form){
        this.form = form;
    }

    /**
     * give you access to the <code>Form</code> Object of <code>MetaAnfForm</code>
     * @return the Form of MetaandForm
     */
    public Form getForm(){
        return this.form;
    }

    /**
     * give you access to the <code>MetaData</code> Object of <code>MetaAnfForm</code>
     * @return the MetaData of MetaandForm
     */
    public MetaData getMeta(){
        return this.meta;
    }


}