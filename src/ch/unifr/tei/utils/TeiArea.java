/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.utils;

import ch.unifr.tei.facsimile.surfacegrp.surface.zone.TeiZone;
import org.jdom2.Element;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * @author Mathias Seuret
 */
public abstract class TeiArea {
    public enum ExtractionMethod {
        POLYGON,
        RECTANGLE
    }
    
    public abstract boolean contains(Point2D p);

    public abstract void addAttributesTo(Element el);

    public abstract Shape getShape();
    
    public abstract BufferedImage extractZoneImage(BufferedImage source, ExtractionMethod method);
}
