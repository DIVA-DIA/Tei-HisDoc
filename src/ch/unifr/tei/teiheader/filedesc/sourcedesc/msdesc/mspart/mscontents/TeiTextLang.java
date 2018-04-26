/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.mscontents;

import ch.unifr.tei.utils.TeiElement;
import org.jdom2.Element;

/**
 *
 * @author Mathias Seuret
 */
public class TeiTextLang extends TeiElement {
    protected String mainLang = null;
    
    public TeiTextLang(TeiMsContents parent) {
        super(parent);
    }
    
    static TeiTextLang load(TeiMsContents parent, Element e) {
        TeiTextLang ttl = new TeiTextLang(parent);
        ttl.mainLang = e.getAttributeValue("mainLang");
        e.removeAttribute("mainLang");
        return ttl;
    }
    
    @Override
    protected String getElementName() {
        return "textLang";
    }

    @Override
    public Element toXML() {
        Element el = getExportElement();
        
        if (mainLang!=null) {
            el.setAttribute("mainLang", mainLang);
        }
        
        return el;
    }
    
    // Accessors
    
    public String getMainLang() {
        return mainLang;
    }
    
    public void setMainLang(String mainLang) {
        this.mainLang = mainLang;
    }
    
}
