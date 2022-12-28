import java.io.*;
import java.util.*;

public class FireBFS {
    static int N, M, K;
    static int[][] map;
    static int[][] visited;
    static int[] dxs = {0, 0, 1, -1};
    static int[] dys = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("fire.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for(int t = 1; t<=T; t++){
            st = new StringTokenizer(br.readLine());
            //변수 입력
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken()); //목적지 개수
            map = new int[N][M];
            visited = new int[N][M];

            for (int[] each : visited) Arrays.fill(each, 0);
            //맵 입력
            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < M; j++) {
                    if(line.charAt(j)=='#'){
                        map[i][j] = 0;
                    }else if(line.charAt(j)=='.'){
                        map[i][j] = 1;
                    }
                }
            }
            //목적지 입력
            int[][] destinations = new int[K][2];
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                destinations[i][0] = Integer.parseInt(st.nextToken()) - 1;
                destinations[i][1] = Integer.parseInt(st.nextToken()) - 1;
            }

            int min = 10000000;

            //목적지에 대해 BFS
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (map[i][j] != 0) {
                        BFS(i, j); //시작점에서 모든 장소의 최단거리를 구한다.
                        List<Integer> temp = new ArrayList<>();
                        for (int[] dest : destinations) {
                            temp.add(visited[dest[0]][dest[1]]); //목적지들의 최단거리를 모은다.
                        } //temp의 최대값이 (i,j)에서의 최단거리이다.
                        if(temp.contains(0)) {
                            continue;

                        }else{min = Math.min(min, Collections.max(temp));}

                        for (int[] each : visited) Arrays.fill(each, 0);
                    }
                }
            }

            if(min==10000000) min = -1;
            //bw.write("#"+t+" "+min+"\n");
            System.out.println("#"+t+ " " + min);
        }

    }

    static void BFS(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        visited[x][y] = 0;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();

            for (int i = 0; i < 4; i++) {
                int newX = curr[0] + dxs[i];
                int newY = curr[1] + dys[i];
                if (isCanGO(newX, newY)) {
                    visited[newX][newY] = visited[curr[0]][curr[1]] + 1;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }
    }

    static boolean isCanGO(int x, int y) {
        return inRange(x, y) && visited[x][y] == 0 && map[x][y] == 1;
    }

    static boolean inRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
