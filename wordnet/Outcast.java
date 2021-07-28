/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;

    }    // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        int[] distance = new int[nouns.length]; // 用于存放节点xi与其他节点距离之和
        for (int i = 0; i < distance.length; i++) {
            String nouni = nouns[i];
            for (int j = 0; j < distance.length; j++)
                if (i != j)
                    distance[i] = distance[i] + wordNet.distance(nouni, nouns[j]);
        }

        int maxDistance = distance[0];
        int maxID = 0;
        for (int i = 0; i < distance.length; i++) {  // 寻找最大距离的节点
            if (maxDistance < distance[i]) {
                maxDistance = distance[i];
                maxID = i;
            }
        }

        return nouns[maxID];
        
    }  // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
