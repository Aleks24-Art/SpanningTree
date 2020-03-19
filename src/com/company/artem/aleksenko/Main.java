package com.company.artem.aleksenko;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.util.Arrays.fill;

public class Main {

    private static double[][] resultGraph;

    public static void main(String[] args) {

        System.out.println(mstPrim());
        System.out.println();
        for (int i = 0; i < resultGraph.length ;i++) {
            System.out.println();
            for (int j = 0; j < resultGraph.length; j++) {
                System.out.printf("%3.0f", resultGraph[i][j]);
            }
        }
    }

    private static int mstPrim() {
        int INF = Integer.MAX_VALUE / 2; // "Бесконечность"
        double[][] graph = getMatrix(); // матрица смежности
        int vNum = graph.length; // количество вершин
        boolean[] used = new boolean [vNum]; // массив пометок
        Dist[] dists = new Dist[vNum];
        //double[] dist = new double [vNum]; // массив расстояния. dist[v] = вес_ребра(MST, v)
        resultGraph = new double[vNum][vNum];
        for (int i = 0; i < vNum; i++) {
            dists[i] = new Dist();
            dists[i].distance = INF;
        }
       // fill(dists[, INF); // устанаавливаем расстояние до всех вершин INF
        dists[0].distance = 0; // для начальной вершины положим 0

        for (;;) {
            int v = -1;
            for (int nv = 0;  nv < vNum; nv++) // перебираем вершины
                if (!used[nv] && dists[nv].distance < INF && (v == -1 || dists[v].distance > dists[nv].distance)) // выбираем самую близкую непомеченную вершину
                    v = nv;
            if (v == -1) break; // ближайшая вершина не найдена
            used[v] = true; // помечаем ее

            for (int nv = 0; nv < vNum; nv++){
                if (!used[nv] && graph[v][nv] < INF) { // для всех непомеченных смежных
                    //System.out.println("v(i)=" + v + " nv(j) = " + nv);
                    if (dists[nv].distance > graph[v][nv]) {
                        dists[nv].distance = graph[v][nv];
                        dists[nv].i = v;
                        dists[nv].j = nv;
                    }
                   /* dists[nv].distance = Math.min(dists[nv].distance, graph[v][nv]); // улучшаем оценку расстояния (релаксация)
                    dists[nv].i = v;
                    dists[nv].j = nv;*/
                }
            }
        }

        int ret = 0; // вес MST
       /* for (int k = 0; k < vNum; k++) {
            for (int i = 0; i < resultGraph.length; i++) {
                for (int j = 0; j < resultGraph.length; j++) {
                    if (dist[k] == graph[i][j]) {
                        resultGraph[i][j] = graph[i][j];
                    }
                }
            }
        }*/

        for (int v = 0; v < dists.length; v++) {
            if (dists[v].i > dists[v].j) {
                resultGraph[dists[v].j][dists[v].i] = dists[v].distance;
            } else {
                resultGraph[dists[v].i][dists[v].j] = dists[v].distance;
            }
            ret += dists[v].distance;
        }
        return ret;
    }


    private static double[][] getMatrix() {
        int size;
        double[][] matrix = null;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            size = Integer.parseInt(reader.readLine());
            matrix = new double[size][size];
            int i;
            int j;
            for (i = 0; i < size; i++) {
                for (j = i + 1; j < size; j++) {
                    System.out.println("Enter the distance between " + (i + 1) +" and " + (j + 1) + " point");
                    double data = Integer.parseInt(reader.readLine());
                    if (data == 0) {
                        matrix[i][j] = Integer.MAX_VALUE;
                        matrix[j][i] = Integer.MAX_VALUE;
                    } else {
                        matrix[i][j] = data;
                        matrix[j][i] = data;
                    }
                }
            }
            for (i = 0; i < size; i++) {
                System.out.println();
                for (j = 0; j < size; j++) {
                    System.out.printf("%10.0f ", matrix[i][j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }
}
