package org.iMage.shutterpile.impl.supplier;

import org.iMage.shutterpile.port.IWatermarkSupplier;

import java.awt.image.BufferedImage;

public abstract class AbstractWatermarkSupplier implements IWatermarkSupplier {
    //template-method allows changes to be made later to all suppliers
    public BufferedImage getWatermark() {
        BufferedImage watermark = createWatermark();
        return watermark;
    }

    public abstract BufferedImage createWatermark();
}
