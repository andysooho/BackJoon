#DFS, BFS 중에서 선택해서 풀면 된다.
import sys
from collections import deque
n, m = map(int, sys.stdin.readline().rstrip().split())
graph = [[] for _ in range(n+1)]

for i in range(m):
    a, b = map(int, sys.stdin.readline().rstrip().split())
    graph[a].append(b)
    graph[b].append(a)

visited = [False] * (n+1)
count = 0

def bfs(graph, start, visited):
    if visited[start]:
        return
    queue = deque([start])
    visited[start] = True
    while queue: #큐가 빌 때까지 반복
        v = queue.popleft()
        for i in graph[v]:
            if not visited[i]:
                queue.append(i)
                visited[i] = True
    global count
    count += 1

for i in range(1, n+1):
    bfs(graph, i, visited)

print(count)

