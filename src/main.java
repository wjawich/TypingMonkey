import java.util.ArrayList;

public class main {

    static float mutationRate;
    static int totalPopulation = 1000;
    static DNA[] population;
    static ArrayList<DNA> matingPool;
    static String target;
    static int generation = 0;
    static boolean found = false;

    public static void main(String[] args) {
        setup("to be or not to be");

        while (!found) {
            generate();
            System.out.println(generation);
        }

    }


    public static void setup(String goal) {
        target = goal;
        mutationRate = (float) 0.01;
        population = new DNA[totalPopulation];
        for (int i =0; i < population.length; i++) {
            population[i] = new DNA();
        }
    }

    public static void generate() {

        generation++;

        ArrayList<DNA> matingPool = new ArrayList<DNA>();

        for (int i = 0; i < population.length; i++) {
            population[i].fitness();

            int n = (int) (population[i].fitness*100);

            for (int j = 0; j < n; j++) {
                matingPool.add(population[i]);
            }
        }

        for (int i =0; i < population.length; i++) {

            int a = (int) random(0,matingPool.size()-1);
            int b = (int) random(0,matingPool.size()-1);


            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);
            DNA child = partnerA.crossover(partnerB);
            child.mutate();
            System.out.println(child.getPhrase());
            population[i] = child;

            if (child.getPhrase().equals(target)) {
                found = true;
                break;
            }

        }

    }


    static class DNA {
        char [] genes;
        float fitness;

         public DNA() {
            genes = new char[target.length()];

            for (int i =0; i < genes.length; i++) {
                genes[i] = (char) random(32,128);
            }

        }

         private void fitness() {
            int score = 0;
            for (int i =0; i < genes.length; i++) {
                if (genes[i] == target.charAt(i)) {
                    score++;
                }
            }

             fitness = (float)(score)/target.length();


        }

        private DNA crossover (DNA partner) {
            DNA child = new DNA();
            int midpoint = (int) random(0,genes.length);

            for (int i = 0; i < genes.length; i++) {
                if (i > midpoint) {
                    child.genes[i] = genes[i];
                } else {
                    child.genes[i] = partner.genes[i];
                }
            }

            return child;
        }

        private void mutate() {
            for (int i=0; i < genes.length; i++) {
                if (random(0,1) < mutationRate) {
                    genes[i] = (char) ((int) random(32,128));
                }
            }
        }

        private String getPhrase() {
            return new String(genes);
        }



    }

    private static double random(int min, int max) {
        return min + (Math.random() * ((max - min) + 1));
    }

}





