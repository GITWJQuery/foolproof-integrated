package com.select.integrated.test;

import java.util.PriorityQueue;

/**
 * 大顶堆
 */
public class MaxHeap {

    public static void main(String[] args) {
        int[] iii = new int[]{2, 7, 4, 1, 8, 1};
        System.out.println(lastStoneWeight(iii));
    }

    public static int lastStoneWeight(int[] stones) {
        int n = stones.length;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(n, (a, b) -> b - a);
        for (int stone : stones) {
            maxHeap.add(stone);
        }

        while (maxHeap.size() >= 2) {
            Integer head1 = maxHeap.poll();
            Integer head2 = maxHeap.poll();
            if (head1.equals(head2)) {
                continue;
            }
            maxHeap.offer(head1 - head2);
        }

        if (maxHeap.isEmpty()) {
            return 0;
        }
        return maxHeap.poll();
    }
}
