/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class WordNet {
    private final ST<String, Bag<Integer>> dictionary;
    private Digraph di;
    private List<String> synset;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        dictionary = new ST<>();
        In synsetsIn = new In(synsets);
        synset = new ArrayList<>();
        int count = 0;

        while (synsetsIn.hasNextLine()) {
            String[] idAndDef = synsetsIn.readLine().split(",");
            int sid = Integer.parseInt(idAndDef[0]);
            String[] synsetsArray = idAndDef[1].split(" ");

            for (String syn : synsetsArray) {
                if (dictionary.contains(syn)) {
                    dictionary.get(syn).add(sid);
                }
                else {
                    Bag<Integer> sids = new Bag<>();
                    sids.add(sid);
                    dictionary.put(syn, sids);
                }
            }
            synset.add(idAndDef[1]);
            count++;
        }

        di = new Digraph(count);

        In hyIn = new In(hypernyms);
        boolean[] isNotRoot = new boolean[count];
        int rootNumber = 0;
        /*
         * hypernym 上义词
         * hyponym 下义词
         * hyponym ---> hypernym
         * */
        while (hyIn.hasNextLine()) {
            String[] relations = hyIn.readLine().split(",");
            int hyponymId = Integer.parseInt(relations[0]);
            isNotRoot[hyponymId] = true;
            for (int i = 1; i < relations.length - 1; i++) {

                int hypernymId = Integer.parseInt(relations[i]);
                di.addEdge(hypernymId, hyponymId);
            }
        }

        for (int i = 0; i < count; i++) {
            if (!isNotRoot[i]) {
                rootNumber++;
            }
        }

        DirectedCycle dc = new DirectedCycle(di);
        if (dc.hasCycle() || rootNumber > 1) {
            throw new IllegalArgumentException();
        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return dictionary.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return dictionary.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        SAP sap = new SAP(di);

        return sap.length(dictionary.get(nounA), dictionary.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        SAP sap = new SAP(di);

        return synset.get(sap.ancestor(dictionary.get(nounA), dictionary.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
