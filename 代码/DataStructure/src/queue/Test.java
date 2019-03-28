package queue;

import java.util.Random;

public class Test {
    public static void main(String[] args){
        int opCount = 1000000;

        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        double time2 = testQueue(loopQueue,opCount);
        System.out.println(time2);

        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        double time1 = testQueue(arrayQueue,opCount);
        System.out.println(time1);


    }

    public static double testQueue(Queue<Integer> q,int opCount){
        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0; i < opCount; i++)
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        for(int i = 0; i < opCount; i++)
            q.dequeue();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }


}
