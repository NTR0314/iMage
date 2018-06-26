package org.iMage.shutterpile.impl;

import org.iMage.shutterpile.port.IFilter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FilterPipeline {
    private ArrayList<IFilter> filterList = new ArrayList<>();

    public void addFilter(IFilter filter) {
        filterList.add(filter);
    }

    public void removeFilter() {
        filterList.clear();
    }

    public BufferedImage run(BufferedImage image) {

        for (IFilter filter:filterList) {
            image = filter.apply(image);
        }

        return image;
    }
}
