import java.io.*;
import java.util.*;

public class BJ2178 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] parts = br.readLine().split(" ");
        int N = Integer.parseInt(parts[0]);
        int M = Integer.parseInt(parts[1]);
        int[][] maze = new int[N][M];

        for(int i=0;i<N;i++){
            parts = br.readLine().split("");
            for(int j=0;j<M;j++){
                maze[i][j] = Integer.parseInt(parts[j]);
            }
        }

        int[] start = {0,0};
        int[] end = {N-1, M-1};

        int answer = minSpaces(maze, start, end);

        System.out.println(answer);

    }

    public static int minSpaces(int[][] maze, int[] start, int[] end){
        // 큐와 방문처리set
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(start);
        Set<String> visited = new HashSet<>();
        visited.add(start[0]+","+start[1]);

        // 최소거리 저장
        int[][] distances = new int [maze.length][maze[0].length];
        for(int[] row : distances){
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        distances[start[0]][start[1]] = 1;

        // BFS
        while(!queue.isEmpty()){

            int size = queue.size();
            for(int i=0;i<size;i++){
                int[] curr = queue.poll();
                int x = curr[0];
                int y = curr[1];

                if(x==end[0] && y==end[1]){
                    return distances[x][y];
                }

                int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
                for(int[] dir : directions){
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    if(newX>=0 && newX<maze.length && newY>=0 && newY<maze[0].length && maze[newX][newY]==1
                            && !visited.contains(newX+","+newY)){
                        queue.add(new int[]{newX, newY});
                        visited.add(newX+","+newY);
                        distances[newX][newY] = Math.min(distances[newX][newY], distances[x][y]+1);
                    }
                }
            }
        }
        return -1; //목적지 도달 불가
    }

}
