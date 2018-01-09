/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier;

import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.msidentifier.altidentifier.TeiAltIdentifier;
import ch.unifr.tei.utils.TeiElement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jdom2.Element;

/**
 * @author Mathias Seuret
 */
public class TeiMsIdentifier extends TeiElement implements TeiIdnoContainer, TeiSettlementContainer {
    private TeiSettlement settlement = new TeiSettlement(this);
    private TeiRepository repository = new TeiRepository(this);
    private TeiIdno idno = new TeiIdno(this);
    private List<TeiAltIdentifier> altIdentifiers = new LinkedList<>();

    public TeiMsIdentifier(TeiElement parent) {
        super(parent);
    }

    private TeiMsIdentifier(TeiElement parent, Element el) {
        this(parent);

        Element e = consumeChild(el, "settlement", TeiNS, true);
        settlement = TeiSettlement.load(this, e);

        e = consumeChild(el, "repository", TeiNS, true);
        repository = TeiRepository.load(this, e);

        e = consumeChild(el, "idno", TeiNS, true);
        idno = TeiIdno.load(this, e);

        do {
            e = consumeChild(el, "altIdentifier", TeiNS, false);
            if (e != null) {
                TeiAltIdentifier aid = TeiAltIdentifier.load(this, e);
                altIdentifiers.add(aid);
            }
        } while (e!=null) ;

        consume(el);
    }

    public static TeiMsIdentifier load(TeiElement parent, Element el) {
        return new TeiMsIdentifier(parent, el);
    }

    @Override
    public String getElementName() {
        return "msIdentifier";
    }

    @Override
    public Element toXML() {
        Element el = getExportElement();

        addContent(el, settlement);
        addContent(el, repository);
        addContent(el, idno);
        for (TeiAltIdentifier aid : altIdentifiers) {
            addContent(el, aid);
        }

        return el;
    }

    @Override
    public void generateDefaultId() {
        // Nothing to do
    }

    
    // ACCESSORS
    
    public String getSettlement() {
        return settlement.getContent();
    }
    
    public void setSettlement(String settlement) {
        this.settlement.setContent(settlement);
    }
    
    public String getRepository() {
        return repository.getContent();
    }
    
    public void setRepository(String repository) {
        this.repository.setContent(repository);
    }
    
    public String getIdno() {
        return idno.getContent();
    }
    
    public void setIdno(String idno) {
        this.idno.setContent(idno);
    }
    
    public List<TeiAltIdentifier> getAltIdentifiers() {
        return Collections.unmodifiableList(altIdentifiers);
    }
    
    public TeiAltIdentifier addAltIdentifier() {
        TeiAltIdentifier aid = new TeiAltIdentifier(this);
        altIdentifiers.add(aid);
        return aid;
    }
    
    public void removeAltIdentifier(TeiAltIdentifier aid) {
        if (!altIdentifiers.remove(aid)) {
            throw new Error("Cannot remove alt identifier \""+aid+"\" because it is not present in the ms identifier");
        }
        aid.notifyDeletion();
    }
}
