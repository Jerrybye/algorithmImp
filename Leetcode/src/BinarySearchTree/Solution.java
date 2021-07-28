package BinarySearchTree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {

            int res = binarySearch(nums, target - nums[i]);
            if (res != -1 && res != i) {

                return new int[]{
                        indexOf(nums, i), indexOf(nums, res)
                };
            }
        }
        return null;
    }

    public int indexOf(int[] nums, int t) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == t) {
                return i;
            }
        }
        return -1;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int t = binarySearch(nums, -(nums[i] + nums[j]));
                if (t != i && t != j) {
                    List<Integer> subRes = new ArrayList<Integer>();
                    subRes.add(nums[i]);
                    subRes.add(nums[j]);
                    subRes.add(nums[t]);
                    res.add(subRes);
                }
            }
        }
        return res;
    }

    public static int binarySearch(int[] nums, int key) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (nums[mid] > key) {
                lo = mid + 1;
            } else if (nums[mid] < key) {
                hi = mid - 1;
            } else {
                return mid;
            }

        }
        return -1;
    }

    public int minDepth(TreeNode root) {
        return 0;
    }

    public static void main(String[] args) {

    }
}
