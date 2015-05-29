package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 07.05.2015.
 *
 * represents a <code>MetaData</code> and <code>Form</code> combination
 * @see MetaData
 * @see Form
 *
 */
public class MetaandForm {

    private MetaData meta;
    private Form form;
    private String signature;

    /**
     * Constructor
     */
    public MetaandForm() {
        signature = "";
    }

    /**
     * set MetaData of a <code>MetaAnfForm</code> Object
     * @param meta <code>MetaData</code> you want to add
     */
    public void setMeta(MetaData meta){
        this.meta = meta;
    }

    /**
     * set Form of a <code>MetaAnfForm</code> Object
     * @param form <code>Form</code> you want to add
     */
    public void setForm(Form form){
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

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignature() {
        return signature;
    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<root>";
        xmlString = xmlString + this.meta.toXMLString();
        xmlString = xmlString + this.form.toXMLString();
        xmlString = xmlString + "<sign text=\""+this.signature +"\" />";
        xmlString = xmlString + "</root>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaandForm that = (MetaandForm) o;

        if (meta != null ? !meta.equals(that.meta) : that.meta != null) return false;
        return !(form != null ? !form.equals(that.form) : that.form != null);

    }

    @Override
    public int hashCode() {
        int result = meta != null ? meta.hashCode() : 0;
        result = 31 * result + (form != null ? form.hashCode() : 0);
        return result;
    }
}