package cloud.model.classroom;

import javax.lang.model.type.ArrayType;
import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Yang ShengYuan
 * @date 2019/3/15
 * @Description ${DESCRIBE}
 **/

public class Map {
    private int numberOfV;
    public Vertex[] vertexArray;
    public double[][] matrix;
    public HashMap<String,Integer> HashingRoom;

    static final String[] room3 = {"310,211,220,0","312,211,395,1","311,211,395,1","314,211,652,2","313,211,652,2","316,211,865,3","315,211,865,3","317,211,1074,4","318,211,1373,5","319,211,1500,6","301,865,1373,7","303,805,1070,8","304,805,845,9","305,805,650,10","306,831,220,11","307,704,220,12"};
    static final String[] room4 = {"410,212,226,0","411,212,396,1","412,212,396,1","413,212,566,2","414,212,566,2","415,212,737,3","416,212,737,3","417,212,892,4","418,212,892,4","419,212,1091,5","420,212,1374,6","421,212,1502,7","422,382,1374,8","401,864,1374,9","402,864,1218,10","403,807,1133,11","404,807,850,12","405,807,650,13","406,850,226,14"};
    static final String[] room5 = {"511,200,223,0","513,200,382.5,1","515,200,567,2","517,200,745.3,3","519,200,918.6,4","521,200,1100,5","512,200,382.5,1","514,200,567,2","516,200,745.3,3","518,200,918.6,4","520,200,1100,5","522,200,1369,6","523,200,1511,7","524,390,1369,8","501,873.3,1369,9","503,797.7,1045,10","504,797.7,835.1,11","505,797.7,641.5,12","506,797.7,395.7,13","507,863.3,223,14","508,700,223,15"};
    static final String[] edge3 = {"0,1","1,2","2,3","3,4","4,5","5,6","5,7","7,8","8,9","9,10","10,11","11,12","12,0"};
    static final String[] edge4 = {"0,1","1,2","2,3","3,4","4,5","5,6","6,7","6,8","8,9","9,10","10,11","11,12","12,13","13,14","14,0"};
    static final String[] edge5 = {"0,1","1,2","2,3","3,4","4,5","5,6","6,7","6,8","8,9","9,10","10,11","11,12","12,13","13,14","14,15","15,0"};



//    public Map(int[][] file1,int[][] file2,int numberOfV){
//
//
//
//        this.numberOfV = numberOfV;
//        this.vertexArray = new Vertex[numberOfV];
//        this.matrix = new double[numberOfV][numberOfV];
//        this.HashingRoom = new HashMap<String, Integer>();
//        for(int i = 0; i<numberOfV ;i++){
//            for(int j = 0; j <numberOfV;j++){
//                if(i==j){
//                    this.matrix[i][j] = 0;
//                }else {
//                    this.matrix[i][j] = Double.MAX_VALUE;
//                }
//            }
//        }
//
//            String[] temp;
//            String line = "";
//            for(int i = 0;i<file1.length;i++) {
//                temp = line.split(",");
//                this.HashingRoom.put(temp[0],Integer.parseInt(temp[3]));
//                if(this.vertexArray[Integer.parseInt(temp[3])]==null){
//                    this.vertexArray[Integer.parseInt(temp[3])]=new Vertex(Double.parseDouble(temp[1]),Double.parseDouble(temp[2]));
//                }
//            }
//
//            String[] temp2;
//            String line2 = "";
//            for(int i = 0;i<file2.length;i++) {
//                temp2 = line2.split(",");
//                addEdge(Integer.parseInt(temp2[0]),Integer.parseInt(temp2[1]));
//            }
//    }

    public Map(int floor){

        String file1[]={};
        String file2[]={};

        if(floor == 3){
            this.numberOfV = 13;
            file1 = room3;
            file2 = edge3;
        }else if(floor == 4){
            this.numberOfV = 15;
            file1 = room4;
            file2 = edge4;
        }else if(floor == 5){
            this.numberOfV = 16;
            file1 = room5;
            file2 = edge5;
        }

        this.vertexArray = new Vertex[numberOfV];
        this.matrix = new double[numberOfV][numberOfV];
        this.HashingRoom = new HashMap<String, Integer>();

        for(int i = 0; i<numberOfV ;i++){
            for(int j = 0; j <numberOfV;j++){
                if(i==j){
                    this.matrix[i][j] = 0;
                }else {
                    this.matrix[i][j] = Double.MAX_VALUE;
                }
            }
        }

        String[] temp;
        String line = "";
        for(int i = 0;i<file1.length;i++) {
            line = file1[i];
            temp = line.split(",");
            this.HashingRoom.put(temp[0],Integer.parseInt(temp[3]));
            if(this.vertexArray[Integer.parseInt(temp[3])]==null){
                this.vertexArray[Integer.parseInt(temp[3])]=new Vertex(Double.parseDouble(temp[1]),Double.parseDouble(temp[2]));
            }
        }

        String[] temp2;
        String line2 = "";
        for(int i = 0;i<file2.length;i++) {
            line2 = file2[i];
            temp2 = line2.split(",");
            addEdge(Integer.parseInt(temp2[0]),Integer.parseInt(temp2[1]));
        }
    }





    private void addEdge(int V1,int V2){
        double weight = vertexArray[V1].weight(vertexArray[V2]);
        this.matrix[V1][V2] = weight;
        this.matrix[V2][V1] = weight;
    }

    public void print(){
        for(int i = 0; i< this.numberOfV;i++){
            for(int j = 0; j < this.numberOfV;j++){
                if((int)this.matrix[i][j]==2147483647){
                    System.out.print("0 ");
                }else{
                    System.out.print((int)this.matrix[i][j]+" ");
                }

            }
            System.out.println();
        }
    }

    public int[] dijkstra(int beg, int des){
        int BEG = this.HashingRoom.get(beg+"");
        int DES = this.HashingRoom.get(des+"");

        boolean[] isIn = new boolean[this.numberOfV];
        double[] distances = new double[this.numberOfV];
        int[] preV = new int[this.numberOfV];

        for (int i = 0 ;i<this.numberOfV;i++){//初始化dijkstra参数
            isIn[i] = false;
            distances[i] =this.matrix[BEG][i];
            if(this.matrix[BEG][i]<Double.MAX_VALUE){
                preV[i] = BEG;
            }else{
                preV[i] = -1;
            }
        }
        preV[BEG] = -1;
        isIn[BEG] = true;

        int u = BEG;
        for(int i = 1; i<this.numberOfV;i++){

            int nextV = u;
            double tempDistance = Double.MAX_VALUE;
            for(int j= 0 ;j<this.numberOfV;j++){
                if((!isIn[j]) && (distances[j]<tempDistance)){
                    nextV = j;
                    tempDistance = distances[j];
                }
            }
            isIn[nextV]  = true;
            u = nextV;

            for(int j = 0;j<this.numberOfV;j++){
                if(!isIn[j] && this.matrix[u][j]< Double.MAX_VALUE){
                    double t = distances[u]+this.matrix[u][j];
                    if(t<distances[j]){
                        distances[j] = t;
                        preV[j] = u;
                    }
                }
            }
        }

        int[] VV = new int[this.numberOfV];
        for(int i = 0;i<this.numberOfV;i++){
            VV[i] = -1;
        }
        int p = DES;
        int count = 0;
        while(true){
            VV[count] = p;
            count++;
            if(p==BEG){
                break;
            }
            p = preV[p];
        }
        ArrayList<Integer> mylist = new ArrayList<>();
        for(int i = this.numberOfV-1 ;i>=0 ;i--){
            if(VV[i]!=-1){
                mylist.add(VV[i]);
            }
        }
        int[] newA = new int[mylist.size()];
        for(int i = 0;i<mylist.size();i++){
            for(Object getkey: HashingRoom.keySet()){
                if(HashingRoom.get(getkey).equals(mylist.get(i))){
                    newA[i] = Integer.parseInt((String)getkey);
                }
            }
        }
        return newA;
    }
}
