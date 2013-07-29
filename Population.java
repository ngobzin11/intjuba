package intjuba.decreasing;

import java.util.Arrays;

public class Population {

    Individual[] individuals;
    private int chromosome_length;

    public Population(int population_size, int chromosome_length, boolean initialize) {
        individuals = new Individual[population_size];
        this.chromosome_length = chromosome_length;

        //Initialize population
        if (initialize) {
            for (int i = 0; i < size(); i++) {
                Individual ind = new Individual();
                ind.generateIndividual(chromosome_length);
                saveIndividual(i, ind);
            }
        }
    }

    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }

    public int size() {
        return individuals.length;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public int getChromosomeLength() {
        return chromosome_length;
    }

    public void setChromosomeLength(int length) {
        chromosome_length = length;
    }

    public Individual[] getFittest(int n) {
        Individual[] fittest = new Individual[n];
        Arrays.sort(individuals);

        for (int i = 0; i < n; i++) {
            fittest[i] = individuals[i];
        }

        return fittest;
    }

    public Individual getFittest() {
        Individual fittest = individuals[0];

        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }

        return fittest;
    }
}
