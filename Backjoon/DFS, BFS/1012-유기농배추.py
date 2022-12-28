# https://www.acmicpc.net/problem/1012
import sys
sys.setrecursionlimit(10**6)  #재귀 깊이를 늘려준다. 백준서버 기본값은 1000이어서 런타임 오류남
t = int(sys.stdin.readline().rstrip())
#이 문제의 교훈. 오랜만에 파이썬 해서 그런지 지도 변수 이름을 map으로 해서 런타임 오류가 났다. ㅡㅡ
#도데체 파이썬은 왜이렇게 근본없는 언어인가.

dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]
def dfs(x, y):
    if x < 0 or x >= n or y < 0 or y >= m:  # 지도를 이탈하면 바로 종료 !!주의: x축이 세로이다.
        return False
    if bcMap[x][y] == 1 and visited[x][y] == False:  # 아직 방문하지 않은곳이면 작업시작
        visited[x][y] = True  # 방문처리
        for i in range(4):  # 상하좌우 탐색
            nx = x + dx[i]
            ny = y + dy[i]
            dfs(nx, ny)
        return True
    return False

for _ in range(t):
    m, n, k = map(int, sys.stdin.readline().rstrip().split())
    baechoos = [list(map(int, sys.stdin.readline().rstrip().split())) for _ in range(k)]
    bcMap = [[0]*m for _ in range(n)]

    for baechoo in baechoos:
      bcMap[baechoo[1]][baechoo[0]] = 1
    #아니 이문제 N,M 거꾸로 되있어서 개헷갈림ㅡㅡ
    visited = [[False] * m for _ in range(n)]

    result = 0
    for i in range(n): #N,M 거꾸로 주의
      for j in range(m):
        if dfs(i,j) == True:
          result += 1

    print(result)
