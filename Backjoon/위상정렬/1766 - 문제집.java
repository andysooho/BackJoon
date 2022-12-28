import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ1766 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
        BufferedWriter bw = new BufferedWriter((new OutputStreamWriter(System.out)));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N= Integer.parseInt(st.nextToken()); //문제의 개수
        int M= Integer.parseInt(st.nextToken()); //먼저 풀어야 하는 문제의 개수
        int[][] solvefirst = new int[M+1][2]; //먼저 풀어야 하는 문제의 정보
        for (int i=0; i<M ; i++){
            st = new StringTokenizer(br.readLine());
            solvefirst[i][0] = Integer.parseInt(st.nextToken());
            solvefirst[i][1] = Integer.parseInt(st.nextToken());
        }

        //int N = 4;
        //int M = 2;
        //int[][] solvefirst = {{4,2},{3,1}}; //먼저 풀어야 하는 문제의 번호

        int[] indegree = new int[N+1]; //진입차수

        ArrayList<Integer>[] graph = new ArrayList[N+1]; //그래프
        for(int i=1; i<=N; i++){
            graph[i] = new ArrayList<>();
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>(); //우선순위 큐

        //문제 a는 문제 b보다 먼저 풀어야 한다.
        for(int i=0; i<M; i++){
            int a = solvefirst[i][0];
            int b = solvefirst[i][1];
            graph[a].add(b);
            indegree[b]++;
        }

        //Topological Sort, 위상정렬
        //진입차수가 0인 노드를 큐에 삽입
        for(int i=1; i<=N; i++){
            if(indegree[i]==0){
                queue.add(i);
            }
        }

        while(!queue.isEmpty()){
            int currProblem = queue.poll();
            //System.out.print(currProblem + " ");
            bw.write(currProblem + " ");

            for(int nextProblem : graph[currProblem]){
                indegree[nextProblem]--;
                if(indegree[nextProblem]==0){
                    queue.add(nextProblem);
                }
            }
        }
        bw.flush(); //버퍼에 쓴 내용을 출력
        bw.close();
    }
}
