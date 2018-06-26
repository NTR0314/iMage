package org.iMage.shutterpile.impl;

import org.iMage.shutterpile.impl.supplier.TextWatermarkSupplier;
import org.iMage.shutterpile.port.IWatermarkSupplier;
import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class TestText {
    private IWatermarkSupplier supplier;
    private BufferedImage result;

    @Before
    public void setUp() {
        this.supplier = new TextWatermarkSupplier("Yamete Oniichan", 100, "Comic Sans MS");
    }

    @Test
    public void test() {
        result = supplier.getWatermark();
        System.out.println("result.getHeight() = " + result.getHeight());
        System.out.println("result.getWidth() = " + result.getWidth());
    }
}
