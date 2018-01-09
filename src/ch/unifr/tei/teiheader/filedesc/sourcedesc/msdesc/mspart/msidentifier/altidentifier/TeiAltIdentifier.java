/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.altidentifier;

import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiIdno;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiIdnoContainer;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiMsIdentifier;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiSettlement;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiSettlementContainer;
import ch.unifr.tei.utils.TeiElement;
import org.jdom2.Element;


/**
 * @author Mathias Seuret
 */
public class TeiAltIdentifier extends TeiElement implements TeiIdnoContainer, TeiSettlementContainer {
    private TeiIdno idno = null;
    private TeiSettlement settlement = null;
    private String type = null;

    public TeiAltIdentifier(TeiElement parent) {
        super(parent);
    }

    private TeiAltIdentifier(TeiMsIdentifier parent, Element el) {
        this(parent);

        Element e = el.getChild("idno", TeiNS);
        if (e!=null) {
            idno = TeiIdno.load(this, e);
        }
        el.removeChild("idno", TeiNS);
        
        e = el.getChild("settlement", TeiNS);
        if (e!=null) {
            settlement = TeiSettlement.load(this, e);
        }
        el.removeChild("settlement", TeiNS);

        type = consumeAttributeStr(el, "type", NoNS, true);

        consume(el);
    }

    public static TeiAltIdentifier load(TeiMsIdentifier parent, Element el) {
        return new TeiAltIdentifier(parent, el);
    }

    @Override
    public String getElementName() {
        return "altIdentifier";
    }

    @Override
    public Element toXML() {
        Element el = getExportElement();

        addContent(el, idno);
        addContent(el, settlement);
        addAttribute(el, "type", type, NoNS);

        return el;
    }

    @Override
    public void generateDefaultId() {
        // Nothing to do
    }
    
    public String getIdno() {
        if (idno==null) {
            return null;
        }
        return idno.getContent();
    }
    
    public void setIdno(String idno) {
        if (this.idno==null) {
            this.idno = new TeiIdno(this);
        }
        this.idno.setContent(idno);
    }
    
    public String getSettlement() {
        if (settlement==null) {
            return null;
        }
        return settlement.getContent();
    }
    
    public void setSettlement(String settlement) {
        if (this.settlement==null) {
            this.settlement = new TeiSettlement(this);
        }
        this.settlement.setContent(settlement);
    }

}
