package controller;

import model.Algorithm;
import model.Chromosome;
import model.Gantt;
import model.utils.NeighborhoodSite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class BA implements Algorithm {
    private int numberOfScouts = 100;
    private int numberOfBestSites = 10;
    private int numberOfEliteSites = 2;

    private int recruitedEliteSites = 40;
    private int recruitedRemainingBestSites = 20;
    private double neighbourhoodSize = 0.5;
    private int siteAbandonment = 5;

    private double neighbourhoodShrink = 0.95;

    private PriorityQueue<Chromosome> scouts = new PriorityQueue<>(numberOfScouts);
    private Gantt bestSolution;

    @Override
    public void run() {
        for(int i = 0; i < numberOfScouts; i++) {
            randomPlaceBee();
        }

        // Neighborhood search...
        // The 2 first are elite, the remaining 8 are best
        ArrayList<NeighborhoodSite> nbSites = new ArrayList<>();

        for(int i = 0; i < generations; i++) {
            // Determine the size of the neighborhood
            for(NeighborhoodSite site : nbSites) {
                site.setPatchSize(site.getPatchSize() * neighbourhoodShrink);
                site.setNoImprovement(site.getNoImprovement()+1);
            }

            for(int site = 0; site < nbSites.size(); site++){
                if(nbSites.get(site).getNoImprovement() >= siteAbandonment){
                    nbSites.remove(site);
                    site -= 1;
                }
            }

            for(int j = 0; j < numberOfBestSites; j++) {
                if(!scouts.isEmpty()) {
                    nbSites.add(new NeighborhoodSite(neighbourhoodSize, scouts.poll()));
                }
            }

            Collections.sort(nbSites);

            while(nbSites.size() > numberOfBestSites) {
                nbSites.remove(nbSites.size() - 1);
            }

            // Recruit for elite sites...
            for (int bee = 0; bee < recruitedEliteSites; bee++){
                int site = (int)(Math.random()*numberOfEliteSites);
                nbSites.get(site).recruitBee();
            }

            // Recruit for RemainingBestSites ...
            for (int bee = 0; bee < recruitedRemainingBestSites; bee++){
                int site = (int)(Math.random()*(numberOfBestSites-numberOfEliteSites))+numberOfEliteSites;
                nbSites.get(site).recruitBee();
            }

            for(NeighborhoodSite n : nbSites){
                if (n.getSite().getFitness() < bestSolution.getFitness()){
                    bestSolution = n.getGantt();
                }
            }
            scouts.clear();
            for(int bee = 0; bee < numberOfScouts-numberOfBestSites; bee++){
                randomPlaceBee();
            }
        }
    }

    private void randomPlaceBee(){
        Chromosome c = new Chromosome();
        c.generateChromosome();
        Gantt gantt = new Gantt();
        gantt.generatePhenoType(c);

        scouts.add(c);

        c.setFitness(gantt.getFitness());
        if(bestSolution == null){
            bestSolution = gantt;
        } else if( gantt.getFitness() < bestSolution.getFitness()){
            bestSolution = gantt;
        }
    }

    public Gantt getBestSolution(){
        return bestSolution;
    }
}
