package intjuba.decreasing;

import java.util.Arrays;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        Population population = new Population(Parameters.population_size, Parameters.std_chromosome_length, true);

        int generation = 0;

        // while (Parameters.shortest_chromosome_length > 6) {
        while (generation < 50) {
            System.out.println("Generation: " + generation);
            System.out.println("###########################");

            Arrays.sort(population.individuals);
            print(population.individuals);

            population = DecreasingChromosomeLengthAlgorithm.evolve(population);
            generation++;
        }

        /*
        int[] solution = {8, 1, 5, 1, 9, 6};

        // Setup Individual
        Individual individual = new Individual();
        individual.generateIndividual(Parameters.std_chromosome_length);

        int index = 0;
        for (Integer gene : solution) {
            individual.replaceGene(index, gene);
            index++;
        }
        System.out.println(individual.toString());

        Game game = new Game();
        int fitness = Parameters.board_height * Parameters.board_width * Parameters.pebbles_per_hole;

        game.simulate(individual.getChromosome());
        fitness -= game.getRemainingPebbles();
        individual.setJunkDNA(game.getSkipped());
        individual.setFitness(fitness);

        System.out.println(
                "Fitness: " + fitness +
                ", Chromosome: " + individual.toString() +
                ", Junk DNA Length: " + individual.getJunkDna().size() +
                ", Actual Length: " + (individual.size() - individual.getJunkDna().size())
        );

        System.out.print("Junk DNA: ");
        for (Integer dna : individual.getJunkDna()) {
            System.out.print(individual.getGene(dna) + ", ");
        }
        System.out.println();

        if (fitness == (Parameters.board_height * Parameters.board_width * Parameters.pebbles_per_hole)) {
            Parameters.solution_exists = true;

            int future_length = individual.size() - game.getSkipped().size();

            // Update the shortest_solution_length variable to the length this solution will be after junk DNA is removed
            if (Parameters.shortest_chromosome_length > future_length) {
                Parameters.shortest_chromosome_length = future_length;
            }
        }

        individual.removeJunkDNA();
        individual.adjustLength(Parameters.shortest_chromosome_length);
        System.out.println("####################################");
        System.out.println(individual.toString());
        */

    }

    public static void print(Individual[] lst) {
        double tot_fitness = 0;
        for (Individual individual : lst) {
            int fitness = individual.getFitness();
            tot_fitness += fitness;
            System.out.println(
                    "Fitness: " + fitness +
                    ", Chromosome: " + individual.toString() +
                    ", Junk DNA Length: " + individual.getJunkDna().size() +
                    ", Actual Length: " + (individual.size() - individual.getJunkDna().size()) +
                    ", isSolution: " + (individual.isSolution() ? "True" : "False")
            );
        }
        System.out.println("Average Fitness: " + tot_fitness / Parameters.population_size);
        System.out.println("Chromosome length: " + Parameters.shortest_chromosome_length);
        System.out.println();
    }
}
