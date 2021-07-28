package StringPlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmazonTest {
    public static List<List<String>> searchSuggestions(List<String> repository, String customerQuery) {
        // Write your code here


        return null;
    }

    //[orange, apple apple, banana orange apple, banana]
    //[apple apple, apple anything apple, apple apple]
    //[kiwi, apple, apple, apple, apple, orange, apple, orange, apple, orange, apple, apple]
    public static int foo(List<String> codeList, List<String> shoppingCart) {
        // Write your code here
        if (codeList.size() == 0) {
            return 1;
        }

        List<String> codeListConcated = new ArrayList<>();

        for (String code : codeList) {
            if (code.contains(" ")) {
                codeListConcated.addAll(Arrays.asList(code.split(" ")));
            } else {
                codeListConcated.add(code);
            }
        }

        int cindex = 0;
        for (int i = 0; i < shoppingCart.size(); i++) {
            if (cindex == codeListConcated.size()) {
                break;
            }

            if (shoppingCart.get(i).equals(codeListConcated.get(i))) {
                cindex++;
            } else {
                cindex = 0;
            }


        }

        return cindex == 0 ? 0 : 1;
    }
}
