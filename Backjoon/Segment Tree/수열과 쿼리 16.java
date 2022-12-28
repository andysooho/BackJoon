import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
    private static int[] tree;
    private static int[] arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        arr = new int[n+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int m = Integer.parseInt(br.readLine()); //쿼리의 개수 M
        tree = new int[4*n+1];

        init(1,n,1);

        while(m-- > 0) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            if(cmd == 1){ // 1 i v : Ai를 v로 바꾼다.
                int index = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                arr[index] = value; //Ai를 v로 바꾼다
                update(1,n,1,index);
            }else if(cmd == 2){ //2 i j : 주어진 범위에서 크기가 가장 작은 값의 인덱스를 출력한다.
                int i = Integer.parseInt(st.nextToken());
                int j = Integer.parseInt(st.nextToken());
                bw.write(query(i,j,1,n,1)+"\n");
            }
        }
        bw.flush();
        bw.close();
    }

    // 세그먼트 트리의 노드 값 초기화
    static int init(int start, int end, int node) { //s부터 e를 관리하는 노드
        if(start == end){ //리프노드 -> 배열의 값 저장 후 리턴
            return tree[node] = start;
        } // ~리프노드 -> 자식노드의 값을 비교해서 minIndex로 설정
        int mid = (start+end)/2;
        return tree[node] = minIndex(init(start,mid,node*2),init(mid+1,end,node*2+1));
    }

    static int update(int start, int end, int node, int index) {
        //if (index가 범위를 벗어남 || 리프 노드에서 수정할 인덱스를 찾았다면) then 현재 노드의 값(arr의 인덱스)를 리턴
        if(index < start || index > end || start==end) {
            return tree[node];
        }// 재비교 실행, 더 작은 값을 가지는 인덱스를 현재 노드에 저장
        int mid = (start+end) / 2;
        return tree[node] = minIndex(update(start,mid,node*2,index),update(mid+1,end,node*2+1,index));
    }

    static int query(int left, int right, int start, int end, int node) {
        // case 1. 범위를 벗어남 : -1 리턴
        if(left > end || right < start) {
            return -1;
        }// case 2. 범위에 완전히 포함 : 현재 노드의 값 리턴 (너는 내가 필요하다굿!)
        if(left <= start && end <= right) {
            return tree[node];
        }// case 3. 양다리: 두 부분으로 나누어서 각각의 최소값을 찾아서 리턴
        int mid = (start+end) / 2;
        return minIndex(query(left,right,start,mid,node*2),query(left,right,mid+1,end,node*2+1));
    }

    static int minIndex(int a, int b) {
        if(a == -1) return b;
        else if(b == -1) return a;
        if(arr[a] == arr[b]){ //값이 같으면 인덱스가 작은 것을 출력
            return Math.min(a, b);
        }
        return arr[a] < arr[b] ? a : b;
    }
}
