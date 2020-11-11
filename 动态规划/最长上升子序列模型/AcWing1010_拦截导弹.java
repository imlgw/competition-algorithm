import java.util.*;
import java.io.*;
/*
某国为了防御敌国的导弹袭击，发展出一种导弹拦截系统。

但是这种导弹拦截系统有一个缺陷：虽然它的第一发炮弹能够到达任意的高度，但是以后每一发炮弹都不能高于前一发的高度。

某天，雷达捕捉到敌国的导弹来袭。

由于该系统还在试用阶段，所以只有一套系统，因此有可能不能拦截所有的导弹。

输入导弹依次飞来的高度（雷达给出的高度数据是不大于30000的正整数，导弹数不超过1000），计算这套系统最多能拦截多少导弹，如果要拦截所有导弹最少要配备多少套这种导弹拦截系统。

输入格式
共一行，输入导弹依次飞来的高度。

输出格式
第一行包含一个整数，表示最多能拦截的导弹数。

第二行包含一个整数，表示要拦截所有导弹最少要配备的系统数。

输入样例：
389 207 155 300 299 170 158 65
输出样例：
6
2

1 2 4 3 2 5 1
 */
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Integer> lis = new ArrayList<>();
        while (sc.hasNext()) {
            lis.add(sc.nextInt());
        }
        int[] w = new int[lis.size()];
        for (int i = 0; i < lis.size(); i++) {
            w[i] = lis.get(i);
        }
        int[] res = solve(w, lis.size());
        System.out.println(res[0]);
        System.out.println(res[1]);
    }

    //300 250 275 252 200 138 245 := 5,2
    //最多击落多少就不多说了
    //最少需要的系统数其实就是最长上升子序列，因为这个子序列是必不可能在同一套系统中被击落
    //当遇到这个子序列中的元素的时候，就需要增加一套系统，所以最少就是这个子序列的长度
    public static int[] solve(int[] w, int N){
        int[] down = new int[N];
        int[] up = new int[N];
        int max = 0, count = 1;
        for (int i = 0; i < N; i++) {
            up[i] = down[i] = 1;
            for (int j = 0; j < i; j++) {
                if (w[j] >= w[i]) {
                    down[i] = Math.max(down[j]+1, down[i]);
                } else {
                    up[i] = Math.max(up[j]+1, up[i]);
                }
            }
            max = Math.max(max, down[i]);
            count = Math.max(count, up[i]);
        }
        return new int[]{max, count};
    }
}