/*
 * Copyright (c) 2016 - UniFr.
 * DIVA group, University of Fribourg, Switzerland.
 */

package ch.unifr.tei.utils;

import org.jdom2.Element;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * @author Mathias Seuret
 */
public class TeiAreaUnlocalized extends TeiArea {

    @Override
    public boolean contains(Point2D p) {
        return false;
    }

    @Override
    public void addAttributesTo(Element el) {
        // Nothing to do.
    }

    @Override
    public Shape getShape() {
        return null;
    }

    @Override
    public BufferedImage extractZoneImage(BufferedImage source, ExtractionMethod method) {
        throw new Error("Cannot extract images from unlocalized zones");
    }

}
