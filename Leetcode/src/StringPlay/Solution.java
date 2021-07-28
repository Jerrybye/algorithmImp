package StringPlay;

import java.util.*;

public class Solution {

    public boolean canPermutePalindrome(String s) {
        int count = 0;

        for (int i = 0; i < 128; i++) {
            int ct = 0;
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == i) {
                    ct++;
                }
            }
            count += ct % 2;
            if (count > 1) {
                break;
            }
        }
        return count <= 1;
    }

    public static boolean isPalindrome(String sub) {
        StringBuilder sb = new StringBuilder(sub);

        return sub.equals(sb.reverse().toString());
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    /*TODO: understand this*/
    private int expandAroundCenter(String s, int left, int right) {
        int L = left, R = right;
        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
            L--;
            R++;
        }
        return R - L - 1;
    }

    /*
    Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

        An input string is valid if:

        Open brackets must be closed by the same type of brackets.
        Open brackets must be closed in the correct order.
    * */
    public boolean isValid(String s) {
        Stack<Character> ps = new Stack<Character>();

        char[] ca = s.toCharArray();

        if (ca.length <= 1) {
            return false;
        }
        for (char c : ca) {
            if (c == '(' || c == '{' || c == '[') {
                ps.push(c);
            }
            try {
                if (c == ')') {
                    char t = ps.pop();
                    if (t != '(') {
                        return false;
                    }
                }

                if (c == '}') {
                    char t = ps.pop();
                    if (t != '{') {
                        return false;
                    }
                }

                if (c == ']') {
                    char t = ps.pop();
                    if (t != '[') {
                        return false;
                    }
                }
            } catch (EmptyStackException e) {
                return false;
            }
        }

        return ps.size() == 0;

    }

    /*
    * Given an input string s, reverse the order of the words.

    A word is defined as a sequence of non-space characters. The words in s will be separated by at least one space.

    Return a string of the words in reverse order concatenated by a single space.

    Note that s may contain leading or trailing spaces or multiple spaces between two words. The returned string should only have a single space separating the words. Do not include any extra spaces.
    * */

    public String reverseWords(String s) {
        String[] subStr = s.split(" ");
        Stack<String> stack = new Stack<String>();

        for (String sub : subStr) {
            if (!sub.equals(" ")) {
                stack.push(sub);
            }
        }

        StringBuilder sb = new StringBuilder();
        Iterator<String> it = stack.iterator();

        while (it.hasNext()) {
            sb.append(stack.pop()).append(" ");
        }
        int n = sb.length();

        return sb.substring(0, n - 1).toString();
    }

    /*
     *Given an encoded string, return its decoded string.

        The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

        You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.

        Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
     * */

    /**/
    public String decodeString(String s) {

        /*
         * Instantiate 2 stacks: 1 for numbers and 1 for characters
         * Tricky part: Since we are iterate a char array the number could coming as 100 being split into '1', '0' '0'
         * Solution for this is keep a temp StringBuilder for number and keep append to it until we hit a '[', that's when we push to number stack
         *
         * Whole idea of solving this is iterate all chars in the string, keep push to number stack and letter stack,
         * when we hit a ']' we pop from number stack for the number we need to proceed the sub string and
         * pop from letters stack for substring until we hit a '['.
         * Most important part is that we need to push the substring back to letters stack for later computation.
         * */
        Stack<Integer> nums = new Stack<>();
        Stack<String> letters = new Stack<>();

        char[] ca = s.toCharArray();

        StringBuilder tempNum = new StringBuilder();
        for (char c : ca) {
            if (Character.isDigit(c)) {
                tempNum.append(c);
            } else if (Character.toString(c).equals("[")) {
                nums.push(Integer.parseInt(tempNum.toString()));
                tempNum = new StringBuilder();
                letters.push(Character.toString(c));
            } else if (!Character.toString(c).equals("]")) {
                // Besides ']' we push all non-numeric value to letters stack
                letters.push(Character.toString(c));
            } else {
                //encounter ]
                Iterator<String> lit = letters.iterator();
                StringBuilder sub = new StringBuilder();
                while (lit.hasNext()) {
                    String temp = letters.pop();
                    if (!temp.equals("[")) {
                        sub.insert(0, temp);
                    } else {
                        break;
                    }

                }
                String t = sub.toString();

                int time = nums.pop();
                while (time > 1) {
                    sub.append(t);
                    time--;
                }
                letters.push(sub.toString());
            }
        }

        StringBuilder res = new StringBuilder();
        Iterator<String> lit = letters.iterator();
        while (lit.hasNext()) {
            res.insert(0, letters.pop());
        }
        return res.toString();
    }

    /*Leet6
    * 0 1 2 3 4 5 6        7->(1,3)
    * P   A   H   N        c = 2i/n-1  r = i%3
      A P L S I I G
      Y   I   R
      *
      * Y  -> F(2) > (2,0)
      * A - >F(4) > (0,2)  3 row  r = 2(4/3)-1,c = (4%3+1)
      * Y -> F(2) > (2,0)
      * P - >F(7) > (1,3)
      *
      * S[0] = c[0][0]
      *
      * while(){
      * }
    * */
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        int n = s.length();
        List<StringBuilder> sbs = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            sbs.add(new StringBuilder());
        }
        int index = 0;
        int rindex = 0;
        boolean goingDown = false;
        while (index < n) {
            sbs.get(rindex).append(s.charAt(index));
            if (rindex == 0 || rindex == numRows - 1) {
                goingDown = !goingDown;
            }
            rindex += goingDown ? 1 : -1;
            index++;

        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : sbs) ret.append(row);
        return ret.toString();
    }

    public static void main(String[] args) {


        Solution ss = new Solution();

        String reverse = ss.convert("AB", 1);

        System.out.println(reverse);

        List<List<Integer>> l = new ArrayList<>();
        l.stream().forEach(list -> {
            list.add();
        });

    }
}
