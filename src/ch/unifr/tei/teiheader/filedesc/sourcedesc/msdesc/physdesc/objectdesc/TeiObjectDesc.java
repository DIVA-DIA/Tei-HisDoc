/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.physdesc.objectdesc;

import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.physdesc.TeiPhysDesc;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.physdesc.objectdesc.layoutdesc.TeiLayoutDesc;
import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.physdesc.objectdesc.supportdesc.TeiSupportDesc;
import ch.unifr.tei.utils.TeiElement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jdom2.Element;

/**
 * @author Mathias Seuret
 */
public class TeiObjectDesc extends TeiElement {
    private List<TeiSupportDesc> supportDescs = new LinkedList<>();
    private TeiLayoutDesc layoutDesc = null;
    private String form = null;

    public TeiObjectDesc(TeiElement parent) {
        super(parent);
    }

    private TeiObjectDesc(TeiPhysDesc parent, Element el) {
        this(parent);

        
        for (Element e : el.getChildren("supportDesc", TeiNS)) {
            supportDescs.add(TeiSupportDesc.load(this, e));
        }
        el.removeChildren("supportDesc", TeiNS);
        

        Element e = consumeChild(el, "layoutDesc", TeiNS, false);
        if (e != null) {
            layoutDesc = TeiLayoutDesc.load(this, e);
//        } else {
//            layoutDesc = new TeiLayoutDesc(this);
        }

        form = consumeAttributeStr(el, "form", NoNS, false);

        consume(el);
    }

    public static TeiObjectDesc load(TeiPhysDesc parent, Element el) {
        return new TeiObjectDesc(parent, el);
    }

    @Override
    public String getElementName() {
        return "objectDesc";
    }

    @Override
    public Element toXML() {
        Element el = getExportElement();

        for (TeiElement te : supportDescs) {
            addContent(el, te);
        }
        addContent(el, layoutDesc);
        addAttribute(el, "form", form, NoNS);

        return el;
    }

    @Override
    public void generateDefaultId() {
        // Nothing to do
    }

    public List<TeiSupportDesc> getSupportDescs() {
        return Collections.unmodifiableList(supportDescs);
    }

}
