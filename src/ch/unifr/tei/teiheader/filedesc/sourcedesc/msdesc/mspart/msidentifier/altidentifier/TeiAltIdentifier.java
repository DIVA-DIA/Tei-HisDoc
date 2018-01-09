/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.altidentifier;

import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiIdno;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiIdnoContainer;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiMsIdentifier;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiRepository;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiRepositoryContainer;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiSettlement;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.TeiSettlementContainer;
import ch.unifr.tei.utils.TeiElement;
import org.jdom2.Element;


/**
 * @author Mathias Seuret
 */
public class TeiAltIdentifier extends TeiElement implements TeiIdnoContainer, TeiSettlementContainer, TeiRepositoryContainer {
    private TeiIdno idno = null;
    private TeiSettlement settlement = null;
    private TeiRepository repository = null;
    private String type = "unknown";

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
        
        e = el.getChild("repository", TeiNS);
        if (e!=null) {
            repository = TeiRepository.load(this, e);
        }
        el.removeChild("repository", TeiNS);

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
        addContent(el, repository);
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
        if (this.idno!=null && idno==null) {
            this.idno.notifyDeletion();
            this.idno = null;
            return;
        }
        if (this.idno==null && idno==null) {
            return;
        } else if (this.idno==null) {
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
        if (this.settlement!=null && settlement==null) {
            this.settlement.notifyDeletion();
            this.settlement = null;
            return;
        }
        if (this.settlement==null && settlement==null) {
            return;
        } else if (this.settlement==null) {
            this.settlement = new TeiSettlement(this);
        }
        this.settlement.setContent(settlement);
    }
    
    public String getRepository() {
        if (repository==null) {
            return null;
        }
        return repository.getContent();
    }

    public void setRepository(String repository) {
        if (this.repository!=null && repository==null) {
            this.repository.notifyDeletion();
            this.repository = null;
            return;
        }
        if (this.repository==null && repository==null) {
            return;
        } else if (this.repository==null) {
            this.repository = new TeiRepository(this);
        }
        this.repository.setContent(repository);
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

}
