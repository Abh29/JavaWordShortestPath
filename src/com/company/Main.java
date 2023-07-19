package com.company;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {


       PathFinder adjM = new PathFinder(new File("src/com/company/Files/dict.txt"));

       adjM.bfs("аист","муха");
       adjM.bfs("било","било");
       adjM.bfs("било","дать");
       adjM.bfs("ящур","дача");

    }
}
