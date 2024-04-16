import java.util.*;
import java.io.*;

public class Main {
    static final int[] dx = {-1, 0, 1, 0, 0};
    static final int[] dy = {0, 1, 0, -1, 0};
    static final int[][][] ch = {{{2,0},{1,-1},{1,1}}, {{0,-2},{1,-1},{-1,-1}}, {{0,2},{1,1},{-1,1}}};

    static int r, c, k;
    static int[][] arr;
    static int[][] visited;
    static int ans;
    static int deep;

    public static void main(String[] args) throws IOException {
		// System.setIn(new FileInputStream("input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        arr = new int[r + 3][c];
        visited = new int[r + 3][c];
        ans = 0;

        int a, b;
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());

            // 들어가기
            int[] result = shoot(b, 1, a-1);
            // 판갈이 체크 결과:갈아야함
            if (result[1] < 4) {
                arr = new int[r + 2][c];
                visited = new int[r+3][c];
            }
			else {
				// 골렘 타고 더 내려가기
				bfs(result, i + 1);

				ans += deep;
			}
        }
        System.out.println(ans);
    }

    static void bfs (int[] res, int time) {
        Deque<int[]> q = new LinkedList<int[]>();
        deep = res[1] - 1;
        boolean[][] visit = new boolean[r+3][c];
        for (int i = 0; i < 5; i++) {
            int nx = res[1] + dx[i];
            int ny = res[2] + dy[i];
            visited[nx][ny] = time;
            visit[nx][ny] = true;
            if(res[0] == i) {
                visited[nx][ny] += 1000;
            }
            q.add(new int[]{nx, ny});
        }

        while (!q.isEmpty()) {
            int[] p = q.poll();
            deep = Math.max(deep, p[0] - 2);

            for (int i = 0; i < 4; i++) {
                int nx = p[0] + dx[i];
                int ny = p[1] + dy[i];
                if (0 <= nx && nx < r+3 && 0 <= ny && ny < c) {
                    if (!visit[nx][ny]) {
                        if (visited[p[0]][p[1]] == visited[nx][ny] || visited[p[0]][p[1]] + 1000 == visited[nx][ny]) {
                            visit[nx][ny] = true;
                            q.add(new int[]{nx, ny});
                        }
                        // 틀린경우
                        else {
                            // 내가 출구이고, 인근이 0이 아니면
                            if (visited[p[0]][p[1]] >= 1000 && visited[nx][ny] != 0) {
                                visit[nx][ny] = true;
                                q.add(new int[]{nx, ny});
                            }
                        }
                    }
                }
            }
        }
    }

    static int[] shoot(int d, int x, int y) {
        // 아래
        if (check(0, x, y)) {
            return shoot(d, x + 1, y);
        } 
        // 좌
        if (check(1, x, y)) {
            return shoot((4+d-1)%4, x + 1, y - 1);
        }
        // 우
        if (check(2, x, y)) {
            return shoot((d+1)%4, x + 1, y + 1);
        }
        return new int[] {d, x, y};
    }

    static boolean check(int d, int x, int y) {
        if (d != 0) {
            for (int i = 0; i < 3; i++) {
                int nx = x + ch[d][i][0];
                int ny = y + ch[d][i][1];
                if (0 > nx || nx >= r + 3 || 0 > ny || ny >= c) return false;
                if (visited[nx][ny] != 0) return false;
            }
            if (d == 1) y--;
            else y++;
        }

        for (int i = 0; i < 3; i++) {
            int nx = x + ch[0][i][0];
            int ny = y + ch[0][i][1];
            if (0 > nx || nx >= r + 3 || 0 > ny || ny >= c) return false;
            if (visited[nx][ny] != 0) return false;
        }
		return true;
    }
}