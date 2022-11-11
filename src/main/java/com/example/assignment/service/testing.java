package com.example.assignment.service;

public class testing {

    public TreeNode pruneTree(TreeNode root) {
        dfs(root);
        return root;
    }

    private boolean dfs(TreeNode root) {
        if (root == null) {
            return true;
        }

        boolean left = dfs(root.left);
        boolean right = dfs(root.right);

        if (root.val == 0 && left && right) {
            return true;
        }

        if (left) {
            root.left = null;
        }
        if (right) {
            root.right = null;
        }

        return left && right;
    }

    public static void main(String[] args) {
        testing ob = new testing();
        TreeNode right2 = new TreeNode(0,null,null);
        TreeNode left2 = new TreeNode(0,null,null);
        TreeNode right1 = new TreeNode(0,left2,right2);
        TreeNode root = new TreeNode(0,null,right1);
        ob.pruneTree( root);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
