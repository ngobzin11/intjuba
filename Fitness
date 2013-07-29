package intjuba;

public class Fitness {

    /**
     * Returns the fitness of a given individual/solution
     *
     * @param individual The solution (Individual) being tested for fitness
     * @return The fitness of the given individual/solution
     */
    static int getFitness(Individual individual) {
        Game game = new Game();
        int fitness = game.getRemainingPebbles() + Parameters.pebbles_per_hole;

        game.simulate(individual.getChromosome());
        fitness -= game.getRemainingPebbles();
        individual.setJunkDNA(game.getSkipped());
        individual.setFitness(fitness);

        if (fitness == (Parameters.board_height * Parameters.board_width * Parameters.pebbles_per_hole)) {
            Parameters.solution_exists = true;
            individual.markAsSolution();

            int useful_length = individual.size() - game.getSkipped().size();

            // Update the shortest_solution_length variable to the length this solution will be after junk DNA is removed
            if (Parameters.shortest_chromosome_length > useful_length) {
                Parameters.shortest_chromosome_length = useful_length;
            }
        }

        return fitness;
    }
}
