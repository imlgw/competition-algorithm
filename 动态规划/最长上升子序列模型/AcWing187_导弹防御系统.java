import java.util.*;
import java.io.*;
/*
为了对抗附近恶意国家的威胁，R国更新了他们的导弹防御系统。

一套防御系统的导弹拦截高度要么一直 严格单调 上升要么一直 严格单调 下降。

例如，一套系统先后拦截了高度为3和高度为4的两发导弹，那么接下来该系统就只能拦截高度大于4的导弹。

给定即将袭来的一系列导弹的高度，请你求出至少需要多少套防御系统，就可以将它们全部击落。

输入格式
输入包含多组测试用例。

对于每个测试用例，第一行包含整数n，表示来袭导弹数量。

第二行包含n个不同的整数，表示每个导弹的高度。

当输入测试用例n=0时，表示输入终止，且该用例无需处理。

输出格式
对于每个测试用例，输出一个占据一行的整数，表示所需的防御系统数量。

数据范围
1≤n≤50
输入样例：
5
3 5 2 4 1
0 
输出样例：
2
样例解释
对于给出样例，最少需要两套防御系统。
一套击落高度为3,4的导弹，另一套击落高度为5,2,1的导弹。
 */

//1. 考虑每个点是在上升序列中还是下降序列中
//2. 考虑是否需要新建一个单独的序列
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            int N = sc.nextInt();
            if (N == 0) return;
            int[] w = new int[N];
            up = new int[N];
            down = new int[N];
            res = Integer.MAX_VALUE;
            for (int i = 0; i < N; i++) {
                w[i] = sc.nextInt();
            }
            //dfs(w, 0, 0, 0);
            int depth = 0;
            while (!dfs(w, depth, 0, 0, 0)) {
                depth++;
            }
            System.out.println(depth);
        }
    }

    static int[] up = null;
    static int[] down = null;
    static int res;

    public static void dfs(int[] w, int c, int ul, int dl) {
        //关键的剪枝
        if (ul+dl >= res) return;
        if (c == w.length) {
            res = Math.min(ul+dl, res);
            return;
        }
        //将c放在上升序列中，那么我们应该找到小于w[i]的最大的tail
        int i = 0;
        for (i = 0; i < ul; i++) {
            if (up[i] < w[c]) break;
        }
        int temp = up[i];
        up[i] = w[c];
        if (i == ul) dfs(w, c+1, ul+1, dl); //单独成新系统
        else dfs(w, c+1, ul, dl); //追加在i后面
        up[i] = temp;
        //将c放在下降序列中，那么我们应该找到大于w[i]的最小的tail
        for (i = 0; i < dl; i++) {
            if (down[i] > w[c]) break;
        }
        temp = down[i];
        down[i] = w[c];
        if (i == dl) dfs(w, c+1, ul, dl+1); //单独成新系统
        else dfs(w, c+1, ul, dl); //追加在i后面
        down[i] = temp;
    }

    //迭代加深，BFS版的DFS
    public static boolean dfs(int[] w, int depth, int c, int ul, int dl) {
        if (ul+dl > depth) return false;
        if (c == w.length) {
            return true;
        }
        //将c放在上升序列中，那么我们应该找到小于w[i]的最大的tail
        int i = 0;
        for (i = 0; i < ul; i++) {
            if (up[i] < w[c]) break;
        }
        int temp = up[i];
        up[i] = w[c];
        if (i == ul) {
            if (dfs(w, depth, c+1, ul+1, dl)) {
                return true;      
            }
        } else if (dfs(w, depth, c+1, ul, dl)) {
            return true;
        }
        up[i] = temp;
        //将c放在下降序列中，那么我们应该找到大于w[i]的最小的tail
        for (i = 0; i < dl; i++) {
            if (down[i] > w[c]) break;
        }
        temp = down[i];
        down[i] = w[c];
        if (i == dl) {
            if (dfs(w, depth, c+1, ul, dl+1)) {
                return true;
            }
        } else if (dfs(w, depth, c+1, ul, dl)) {
            return true;
        }
        down[i] = temp;
        return false;
    }
}