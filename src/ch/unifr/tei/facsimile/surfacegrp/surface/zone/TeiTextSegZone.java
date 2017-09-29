/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.facsimile.surfacegrp.surface.zone;

import ch.unifr.tei.text.body.div.div.ab.TeiAb;
import ch.unifr.tei.utils.TeiArea;
import java.awt.image.BufferedImage;
import org.jdom2.Element;

import java.util.Collections;
import java.util.List;
import org.javatuples.Pair;

/**
 * @author Mathias Seuret
 */
public class TeiTextSegZone extends TeiZone<TeiTextZone, TeiWordZone> {

    private TeiTextZone parent;
    
    protected TeiAb transcription = null;

    public TeiTextSegZone(TeiTextZone parent, TeiArea area) {
        super(parent, area);
        this.parent = parent;
    }

    private TeiTextSegZone(TeiTextZone parent, Element el) {
        super(parent, el);
        this.parent = parent;
    }

    public static TeiTextSegZone load(TeiTextZone parent, Element el) {
        return new TeiTextSegZone(parent, el);
    }

    @Override
    protected void loadSubZones(Element el) {
        for (Element e : el.getChildren("zone", TeiNS)) {
            zones.add(TeiWordZone.load(this, e));
        }
        el.removeChildren("zone", TeiNS);
    }
    
    public void setTranscription(TeiAb ab) {
        if (ab==transcription) {
            if (ab.getFacs()!=this) {
                ab.setFacs(this);
                updateResp();
                ab.updateResp();
            }
            return;
        }
        updateResp();
        
        if (transcription!=null) {
            transcription.setFacs(null);
        }
        transcription = ab;
        if (transcription!=null && transcription.getFacs()!=this) {
            transcription.setFacs(this);
        }
    }
    
    public TeiAb getTranscription() {
        return transcription;
    }
    
    public void initializeTranscription() {
        if (transcription!=null) {
            return;
        }
        if (parent.getTranscription()==null) {
            parent.initializeTranscription();
        }
        updateResp();
        transcription = parent.getTranscription().addAb();
        transcription.setFacs(this);
    }
    
    @Override
    public void notifyDeletion() {
        super.notifyDeletion();
        for (TeiZone z : zones) {
            z.notifyDeletion();
        }
        if (transcription!=null) {
            transcription.setFacs(null);
            transcription.notifyDeletion();
        }
    }

    @Override
    public void generateDefaultId() {
        String pid = parent.getId();
        if (pid == null) {
            pid = "";
        }
        int n = parent.getIndex(this) + 1;
        setId(pid + "l" + n);
    }

    @Override
    public int getIndex(TeiWordZone child) {
        return zones.indexOf(child);
    }

    /*
        ACCESSORS
     */

    /**
     * Adds a new text zone in the page.
     *
     * @param area containing the zone
     * @return the newly created text zone
     */
    public TeiWordZone addWordZone(TeiArea area) {
        TeiWordZone t = new TeiWordZone(this, area);
        t.updateResp();
        updateResp();
        zones.add(t);
        t.generateDefaultId();
        return t;
    }

    public void removeWordZone(TeiWordZone z) {
        if (zones.contains(z)) {
            zones.remove(z);
            unregisterId(z.getId());
            regenerateWordIDs();
            updateResp();
        }
    }

    private void regenerateWordIDs() {
        for (TeiWordZone t : zones) {
            t.generateDefaultId();
        }
    }

    public TeiTextZone getParent() {
        return parent;
    }

    public List<TeiWordZone> getWordZones() {
        return Collections.unmodifiableList(zones);
    }
    
    public Pair<BufferedImage, String> getOcropyTranscription(BufferedImage baseImage, TeiArea.ExtractionMethod extractionMethod) {
        if (transcription==null) {
            throw new Error("No transcription fro getting Ocropy transcription");
        }
        String tr = transcription.toString();
        BufferedImage zi = area.extractZoneImage(baseImage, extractionMethod);
        return new Pair<>(zi, tr);
    }

}
