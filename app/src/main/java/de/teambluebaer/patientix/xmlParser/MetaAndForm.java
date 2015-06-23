package de.teambluebaer.patientix.xmlParser;



/**
 * Copyright 2015 By Authors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 * Simon Sauerzapf
 * Maren Dietrich
 * Chris Harsch
 *
 * represents a <code>MetaData</code> and <code>Form</code> combination
 * @see MetaData
 * @see Form
 *
 */
public class MetaAndForm {

    private MetaData meta;
    private Form form;
    private String signature;

    /**
     * Constructor
     */
    public MetaAndForm() {
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
     * @return the Form of MetaAndForm
     */
    public Form getForm(){
        return this.form;
    }

    /**
     * give you access to the <code>MetaData</code> Object of <code>MetaAnfForm</code>
     * @return the MetaData of MetaAndForm
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
        xmlString = xmlString + "<sign image=\""+this.signature +"\" />";
        xmlString = xmlString + "</root>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetaAndForm that = (MetaAndForm) o;

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
