package intjuba;

import java.util.LinkedList;
import java.util.Random;

public class Individual implements Comparable<Individual> {

    private LinkedList<Integer> chromosome = new LinkedList<Integer>();
    Random rand_generator = new Random();
    static int chromosome_length;
    private int fitness = 0;

    // These are the starting points that had no pebbles in the pocket
    private LinkedList<Integer> junk_dna = new LinkedList<Integer>();

    // Whether the individual is a solution or not. Initialized as false
    private boolean solution = false;

    public void generateIndividual(int length) {
        chromosome_length = length;

        for (int i = 0; i < chromosome_length; i++) {
            int gene = rand_generator.nextInt(10);
            while (gene == 0) {
                gene = rand_generator.nextInt(10);
            }

            chromosome.add(gene);
        }
    }

    public int size() {
        return chromosome.size();
    }

    public boolean isSolution() {
        return solution;
    }

    public void markAsSolution() {
        solution = true;
    }

    public int getGene(int index) {
        return chromosome.get(index);
    }

    public void setGene(int index, int value) {
        chromosome.add(index, value);
        fitness = 0;
    }

    public void replaceGene(int index, int value) {
        chromosome.set(index, value);
    }

    public LinkedList<Integer> getChromosome() {
        return chromosome;
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = Fitness.getFitness(this);
        }
        return fitness;
    }

    public void setFitness(int fitness){
        this.fitness = fitness;
    }

    public void setJunkDNA(LinkedList<Integer> dna) {
        junk_dna = dna;
    }

    public void removeJunkDNA() {
        if (junk_dna.size() == 0) {
            return;
        }

        LinkedList<Integer> new_chromosome = new LinkedList<Integer>();

        for (Integer index : junk_dna) {
            replaceGene(index, -1);
        }

        for (Integer gene : chromosome) {
            if (gene != -1) {
                new_chromosome.add(gene);
            }
        }

        chromosome = new_chromosome;
        chromosome_length = chromosome.size();
    }

    public LinkedList<Integer> getJunkDna() {
        return junk_dna;
    }

    public void adjustLength(int length) {
        chromosome_length = chromosome.size();
        if (chromosome_length > length) {

            // Remove last few genes to make chromosome long enough
            for (int i = chromosome_length - 1; i > length; i--) {
                chromosome.remove(i);
            }
        } else if (chromosome_length < length) {

            // Add some randomly generated genes to make the chromosome long enough
            while (chromosome_length < length) {
                int gene = rand_generator.nextInt(Parameters.board_height * Parameters.board_width);
                while (gene == Parameters.home) {
                    gene = rand_generator.nextInt(Parameters.board_height * Parameters.board_width);
                }
                chromosome.add(gene);
                chromosome_length++;
            }
        }

        // The fitness of the individuals is most likely different from what it was earlier so it needs to be reset
        fitness = 0;
    }

    @Override
    public String toString() {
        return chromosome.toString();
    }

    @Override
    public int compareTo(Individual individual) {

        // Ascending order
        return individual.getFitness() - this.getFitness();
    }
}
