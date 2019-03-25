package model.utils;

import controller.Main;
import model.Chromosome;
import model.Gantt;

public class NeighborhoodSite implements Comparable<NeighborhoodSite> {
    private double patchSize;

    private Gantt gantt;
    private Chromosome site;
    private int noImprovement = 0;

    public NeighborhoodSite(double patchSize, Chromosome site, Gantt gantt) {
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

    @Override
    public int compareTo(NeighborhoodSite o) {
        double sum = this.site.getFitness() - o.site.getFitness();
        if(sum > 0){
            return 1;
        }else if(sum == 0){
            return 0;
        }
        return -1;
    }

    public void recruitBee() {
        Chromosome c =  new Chromosome(Main.n, Main.n);
        c.generateFromSite(site);
        Gantt gantt = new Gantt();
        gantt.generatePhenoType(c);

        c.setFitness(gantt.getFitness());

    }
}
