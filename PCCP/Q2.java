import java.util.*;

class Solution {
    private void dfs(int[][] land, boolean[][] visited, int i, int j, BitSet columns, int[] size) {
        if (i < 0 || i >= land.length || j < 0 || j >= land[0].length || visited[i][j] || land[i][j] == 0) {
            return;
        }

        visited[i][j] = true;
        size[0]++;
        columns.set(j);

        dfs(land, visited, i + 1, j, columns, size);
        dfs(land, visited, i - 1, j, columns, size);
        dfs(land, visited, i, j + 1, columns, size);
        dfs(land, visited, i, j - 1, columns, size);
    }

    public int solution(int[][] land) {
        int n = land.length;
        int m = land[0].length;
        boolean[][] visited = new boolean[n][m];
        Map<BitSet, Integer> oilGroups = new HashMap<>();

        // 모든 격자를 순회하며 석유 덩어리를 탐색합니다.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (land[i][j] == 1 && !visited[i][j]) {
                    BitSet columns = new BitSet(m);
                    int[] size = {0};
                    dfs(land, visited, i, j, columns, size);

                    oilGroups.put(columns, oilGroups.getOrDefault(columns, 0) + size[0]);
                }
            }
        }

        // 각 열의 석유량을 계산합니다.
        int[] maxOilPerColumn = new int[m];
        for (Map.Entry<BitSet, Integer> entry : oilGroups.entrySet()) {
            BitSet columns = entry.getKey();
            int size = entry.getValue();
            for (int j = columns.nextSetBit(0); j != -1; j = columns.nextSetBit(j + 1)) {
                maxOilPerColumn[j] += size;
            }
        }

        // 최대 석유량을 찾습니다.
        return Arrays.stream(maxOilPerColumn).max().orElse(0);
    }

    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] land = {
            {0, 0, 0, 1, 1, 1, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0},
            {1, 1, 0, 0, 0, 1, 1, 0},
            {1, 1, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 0, 1, 1}
        };

        int result = sol.solution(land);
        System.out.println("Result: " + result);
    }
}
