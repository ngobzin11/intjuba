package intjuba;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DecreasingChromosomeLengthAlgorithm {

    /* GA parameters */
    private static double locus;
    private static final double uniform_rate = 0.5;
    private static final double mutation_rate = 0.5;

    static Random rand_generator = new Random();
    private static int population_size;

    public static Population evolve(Population pop) {
        int index = 0;

        // Set fitness for all individuals
        for (Individual individual : pop.individuals) {
            individual.getFitness();
            pop.saveIndividual(index, individual);
            index++;
        }

        // Adjust individual length if and only if solution exists
        if (Parameters.solution_exists) {
            index = 0;
            for (Individual individual : pop.individuals) {
                individual.removeJunkDNA();
                individual.adjustLength(Parameters.shortest_chromosome_length);
                pop.saveIndividual(index, individual);
                index++;
            }
        }

        // Adjust the crossover locus
        locus = uniform_rate * Parameters.shortest_chromosome_length;

        // Get the fittest half of the population
        Individual[] fittest = shuffle(pop.getFittest(Parameters.population_size / 2));
        Population next_generation = new Population(Parameters.population_size, Parameters.shortest_chromosome_length, false);

        index = 0;
        for (Individual individual : fittest) {
            next_generation.saveIndividual(index, individual);
            index++;
        }

        // Generate new individuals by crossing over parents
        for (int i = 0; i < (Parameters.population_size / 4); i++) {
            Individual[] children = topLocusCrossover(fittest[2 * i], fittest[(2 * i) + 1]);

            // Add first child
            next_generation.saveIndividual(index, children[0]);
            index++;

            // Add second child
            next_generation.saveIndividual(index, children[1]);
            index++;
        }

        // Future implementation: Change mutation rate based on solution similarity
        // Mutate population
        index = 0;
        for (Individual individual : next_generation.individuals) {
            // If don't want to mutate solutions
            // if (!individual.isSolution()) {
                individual = mutate(individual);
            //}
            next_generation.saveIndividual(index, individual);
            index++;
        }

        // Reset solution exists variable in case solution didn't become one after mutation
        Parameters.solution_exists = false;

        return next_generation;
    }

    private static Individual[] topLocusCrossover(Individual parent1, Individual parent2) {
        Individual[] children = {new Individual(), new Individual()};
        int count;

        for (count = 0; count < locus; count++) {
            children[0].setGene(count, parent1.getGene(count));
            children[1].setGene(count, parent2.getGene(count));
        }

        for (; count < Math.min(parent1.size(), parent2.size()); count++) {
            children[0].setGene(count, parent2.getGene(count));
            children[1].setGene(count, parent1.getGene(count));
        }

        return children;
    }

    // Mutate an individual
    private static Individual mutate(Individual individual) {
        // Loop through genes
        for (int i = 0; i < Parameters.shortest_chromosome_length; i++) {
            if (Math.random() <= mutation_rate) {
                int gene = rand_generator.nextInt(10);
                while (gene == 0) {
                    gene = rand_generator.nextInt(10);
                }
                individual.replaceGene(i, gene);
            }
        }
        return individual;
    }

    private static Individual[] shuffle(Individual[] fittest) {
        // Convert Array to Array List
        ArrayList<Individual> lst = new ArrayList<Individual>();
        for (Individual individual : fittest) {
            lst.add(individual);
        }
        Collections.shuffle(lst, rand_generator);

        // Convert back to array
        int index = 0;
        for (Individual individual : lst) {
            fittest[index] = individual;
            index++;
        }

        return fittest;
    }

}
