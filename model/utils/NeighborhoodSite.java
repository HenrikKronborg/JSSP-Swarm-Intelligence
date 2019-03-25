package model.utils;

import model.Chromosome;

public class NeighborhoodSite {
    private double patchSize;

    private Chromosome site;
    private int noImprovement = 0;

    public NeighborhoodSite(double patchSize, Chromosome site) {
        this.patchSize = patchSize;
        this.site = site;
    }

    public double getPatchSize() {
        return patchSize;
    }

    public void setPatchSize(double patchSize) {
        this.patchSize = patchSize;
    }

    public Chromosome getSite() {
        return site;
    }

    public void setSite(Chromosome site) {
        this.site = site;
    }

    public int getNoImprovement() {
        return noImprovement;
    }

    public void setNoImprovement(int noImprovement) {
        this.noImprovement = noImprovement;
    }
}
