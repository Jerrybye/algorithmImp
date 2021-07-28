package LinkedList;

public class Solution {
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode reverseList(ListNode n) {
        if (n.next == null || n == null) {
            return n;
        }
        //base case

        ListNode t = reverseList(n.next);
        n.next.next = n;
        n.next = null;
        return t;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode nl1 = reverseNode(l1);
        ListNode nl2 = reverseNode(l2);
        return null;
    }

    public ListNode reverseNode(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }
        ListNode p = reverseNode(head.next);
        head.next.next = head.next;
        head.next = null;
        return p;
    }

    public int toNumber(ListNode t) {
        StringBuilder bs = new StringBuilder(123);
        while (t.next != null) {
            bs.append(t.val);
        }

        return Integer.parseInt(bs.toString());
    }


    public static void main(String[] args) {
//        ListNode n = new ListNode(1);
//        n.next = new ListNode(2);
//
//        Solution s = new Solution();
//        s.reverseList(n);
//        String s = "asd";
//        StringBuilder sb = new StringBuilder(s);
//        sb.reverse();
    }
}
