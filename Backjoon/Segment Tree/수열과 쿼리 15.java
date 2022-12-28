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
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int m = Integer.parseInt(br.readLine()); //쿼리의 개수 M

        tree = new int[4*n+1];

        init(0,n-1,1);
        while(m-- > 0) {
            st = new StringTokenizer(br.readLine());
            int cmd, index, value;
            cmd = Integer.parseInt(st.nextToken());
            if(cmd == 2){ //2 : 수열에서 크기가 가장 작은 값의 인덱스를 출력한다.
                bw.write(tree[1]+1+"\n"); //인덱스는 1부터 시작하므로 +1
            }else{ // 1 i v : Ai를 v로 바꾼다.
                index = Integer.parseInt(st.nextToken())-1;
                value = Integer.parseInt(st.nextToken());
                arr[index] = value; //Ai를 v로 바꾼다
                update(0,n-1,1,index);

            }
        }
        bw.flush();
        bw.close();
    }

    // 세그먼트 트리의 노드 값 초기화(설정)
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
        }
        int mid = (start+end) / 2;
        // 재비교 실행, 더 작은 값을 가지는 인덱스를 현재 노드에 저장
        return tree[node] = minIndex(update(start,mid,node*2,index),update(mid+1,end,node*2+1,index));

    }

    static int minIndex(int a, int b) {
        if(arr[a] == arr[b]){ //값이 같으면 인덱스가 작은 것을 출력
            return Math.min(a, b);
        }
        return arr[a] < arr[b] ? a : b;
    }
}

        /*
        세그먼트 트리 시각적으로 이해하기
        https://www.geeksforgeeks.org/segment-tree-range-minimum-query/
        세그먼트 트리의 재귀 및 비재귀 구현
        https://www.youtube.com/watch?v=Afr0yvd-8bA

        • 루트 노드의 번호는 1
        • 노드 x의 자식 : 2x, 2x+1
        • 노드 x의 부모 :[x/2]
        • 수열 길이가 2^k일때 배열 크기:2^(k+1)

        • 세그먼트 트리의 높이 = logN(밑이 2인 로그)의 값을 올림 한 후 + 1
        • 세그먼트 트리의 전체 노드 수 = 2^(트리의 높이)
        */
