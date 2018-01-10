/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.mscontents;

import ch.unifr.tei.teiheader.filedesc.sourcedesc.msdesc.mspart.mscontents.msitem.TeiMsItem;
import ch.unifr.tei.utils.TeiElement;
import java.util.Collections;
import org.jdom2.Element;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mathias Seuret
 */
public class TeiMsContents extends TeiElement {
    private List<TeiMsItem> msItems = new LinkedList<>();
    TeiTextLang textLang = null;

    public TeiMsContents(TeiElement parent) {
        super(parent);
    }

    private TeiMsContents(TeiElement parent, Element el) {
        this(parent);

        for (Element e : el.getChildren("msItem", TeiNS)) {
            msItems.add(TeiMsItem.load(this, e));
        }
        el.removeChildren("msItem", TeiNS);
        
        Element e = el.getChild("textLang", TeiNS);
        if (e!=null) {
            textLang = TeiTextLang.load(this, e);
        }
        el.removeChild("textLang", TeiNS);

        consume(el);
    }

    public static TeiMsContents load(TeiElement parent, Element el) {
        return new TeiMsContents(parent, el);
    }

    @Override
    public String getElementName() {
        return "msContents";
    }

    @Override
    public Element toXML() {
        Element el = getExportElement();
        
        addContent(el, textLang);

        for (TeiElement e : msItems) {
            el.addContent(e.toXML());
        }

        return el;
    }

    @Override
    public void generateDefaultId() {
        // Nothing to do
    }

    public TeiTextLang createTextLang() {
        textLang = new TeiTextLang(this);
        return textLang;
    }
    
    public List<TeiMsItem> getMsItems() {
        return Collections.unmodifiableList(msItems);
    }
    
    public TeiMsItem createMsItem() {
        TeiMsItem msi = new TeiMsItem(this);
        msItems.add(msi);
        return msi;
    }
    
    public void removeMsItem(TeiMsItem msi) {
        if (msItems.contains(msi)) {
            msItems.remove(msi);
        } else {
            throw new Error("Cannot remove ms item as it is not in the list");
        }
    }

}
