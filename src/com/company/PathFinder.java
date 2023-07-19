package com.company;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class PathFinder {

    File dict ;
    boolean[][] adjMat ;
    private List<String> dictionaryList ;
    private boolean initiated = false ;



    public PathFinder( File dict){
        this.dict = dict;
    }

    private boolean areAdjacent(String src , String dst){
        boolean differ = false ;

        if (src.length() == dst.length()){
            for (int i = 0; i < src.length(); i++) {
                if(src.charAt(i) != dst.charAt(i)){
                    if (differ)
                        return false;
                    differ = true;
                }
            }

            if (differ)
                return true;

        }

        return false;
    }

    private void dictionaryList() throws IOException {
        Scanner scanner = new Scanner(dict);
        dictionaryList = new ArrayList<>(2000);

        while (scanner.hasNext()){
            dictionaryList.add(scanner.next().toLowerCase());
        }
        scanner.close();

    }

    private List<String> adjContenders(String word){
        char[] alphabet = {'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я'};   // for russian dictionary

     //   char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();  // for english dictionary

        ArrayList<String> out = new ArrayList<>(alphabet.length * word.length() + 1);

        for (int i = 0; i < word.length(); i++) {
            for (char letter: alphabet) {
                out.add(word.substring(0,i) + letter + word.substring(i+1));
            }
        }

        return out;
    }

    private void dictionarySort(){

        dictionaryList.sort((this::compare));


    }

    private int compare(String word1 , String word2){
        int size = Math.min(word1.length(), word2.length());
        int  c1, c2;

        for (int i = 0; i < size; i++) {
            c1 = (c1 = word1.charAt(i)) == 'ё' ? 'е' * 2 +1 : c1 * 2 ;
            c2 = (c2 = word2.charAt(i)) == 'ё' ? 'е' * 2 +1 : c2 * 2 ;

            if (c1 != c2)
                return c1 - c2;
        }

        if (word1.length() == word2.length())
              return 0;
        else
            return word1.length() - word2.length();
    }

    private int binarySearch(String word){

        int low = 0 ;
        int high = dictionaryList.size() - 1 ;
        int middle;

        if (compare(dictionaryList.get(low),word) > 0 || compare(dictionaryList.get(high),word) < 0 )
            return -1;

        if (compare(dictionaryList.get(low),word) == 0 )
            return low;

        if (compare(dictionaryList.get(high),word) == 0 )
            return high;



        while( high - low > 1 ){
            middle = (low + high + 1 ) / 2 ;

            if (compare(dictionaryList.get(middle),word) < 0){
                low = middle;
            }else if (compare(dictionaryList.get(middle),word) > 0){
                high = middle;
            }else {
                return middle;
            }

        }



        return -1;
    }

    public void initiate() throws IOException{
        dictionaryList();
        dictionarySort();
        initiated =true;
    }

    public void setAdjMat() throws IOException{
        if(!initiated)
            initiate();  // n*log(n)
        int index ;
        int i = 0 ;

        adjMat = new boolean[dictionaryList.size()][dictionaryList.size()];

        for (String src : dictionaryList) {                 //n

            for (String dest : adjContenders(src)) {        //32m

                index = binarySearch(dest);                 //log(n)
                if (index != -1 && !src.equals(dest)){
                    adjMat[i][index] = true;
                }
            }

            i++;

        }

        //O(nm*log(n)               in this case m = 4    =>  O(nlog(n))


    }

    public boolean[][] getAdjMat() throws IOException {
        if (adjMat == null)
            setAdjMat();
        return adjMat;
    }

    public List<String> adjList(String word){
        ArrayList<String> out = new ArrayList<>();

        for (String s : adjContenders(word)) {
            if (binarySearch(s) != -1 && !word.equals(s)){
                out.add(s);
            }
        }

        return out;
    }

    public void bfs(String src , String dest) throws IOException{

        if (!initiated)
            initiate();

        Queue<String> reachable = new ArrayDeque<>(dictionaryList.size());
        boolean[] visited = new boolean[dictionaryList.size()];
        String[] pere = new String[dictionaryList.size()];
        List<String> path = new ArrayList<>();
        String current = "";
        int indx ;
        boolean found = false ;



        if (binarySearch(src) == -1 || binarySearch(dest) == -1 )
            System.out.println("there is no path from " + src + " to " + dest + " in the dictionary !");

        else if (src.equals(dest))
            System.out.println("[ "+ src +" ]");


        else {
                reachable.add(src);


            while(!reachable.isEmpty() && !found){

                current = reachable.remove();


                if (adjList(current).contains(dest))
                    found = true;

                for (String word: adjList(current)) {
                    indx = binarySearch(word);
                    if(!visited[indx]){
                        reachable.add(word);
                        visited[indx] = true;
                        pere[indx] = current ;
                    }

                }

            }

            if (!found){
                System.out.println("there is no path from " + src + " to " + dest + " in the dictionary !");
            }else{

                path.add(dest);

                do{
                    path.add(current);
                    current = pere[binarySearch(current)];

                }while (current != null && !current.equals(src));

                if (!path.contains(src))
                    path.add(src);

                System.out.println(path);

            }



        }


    }


}
