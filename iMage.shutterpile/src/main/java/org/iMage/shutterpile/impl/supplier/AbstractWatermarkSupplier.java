package org.iMage.shutterpile.impl.supplier;

import org.iMage.shutterpile.port.IWatermarkSupplier;

import java.awt.image.BufferedImage;

public abstract class AbstractWatermarkSupplier implements IWatermarkSupplier {
    //TODO implement as template-method
    public BufferedImage getWatermark() {
        BufferedImage watermark = createWatermark();
        return null;
    }

    public abstract BufferedImage createWatermark();
}
