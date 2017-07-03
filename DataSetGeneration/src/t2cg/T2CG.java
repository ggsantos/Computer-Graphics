/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2cg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author user
 */
public class T2CG {
    
    static ArrayList<Person> crowd = new ArrayList<>();
    static int pixels;

    private static class Person{
        
        String name;
        HashMap<Integer, Integer[]> place;
        boolean flag;
        
        public Person(String newName){
            
            name = newName;
            place = new HashMap<Integer, Integer[]>();
            flag = false;
            
        }
        
        public void createMap(String data){
            
            //data.replaceAll("\\\\(.+\\\\)", " ");
            String data2 = data.replaceAll("[()]", " ");
            data2 = data2.trim();
            String[] s = data2.split("  ");
            
            for(int i = 0; i < s.length; i++){
                
                String[] s2 = s[i].split(",");
                
                Integer[] posision = new Integer[2];
                
                //if(i == 0) posision[0] = Integer.parseInt(s2[0].substring(1));
                //else 
                posision[0] = Integer.parseInt(s2[0]);
                posision[1] = Integer.parseInt(s2[1]);
                
                place.put(Integer.parseInt(s2[2]), posision);
                
            }
            
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner tec = new Scanner(System.in);
        
        System.out.println("Digite o nome do arquivo que deseja utilizar");
        
        File path_d = new File(tec.nextLine());
        
        createMaps(path_d);
        
        int multDensity = 2;
        int minDistance = 1;
        int maxDistance = 5;
        
        riseDensity(multDensity, minDistance, maxDistance);
        collisionAvoidance();
        
        BufferedWriter writer = null;
        try {

            //Cria novo arquivo e mostra onde foi criado
            File file = new File("Out.txt");
            //System.out.println(file.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(file));
            
            writer.write("[" + pixels + "]");
            writer.newLine();
            
            for(Person p : crowd){
                writer.write(p.place.size() + "\t");
                Set<Integer> s = p.place.keySet();
                for(Integer key : s){
                    Integer[] array = p.place.get(key);
                    writer.write("(" + array[0] + "," + array[1] + "," + key + ")");
                }
                writer.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
        
    }
    
    public static void createMaps(File path_d) throws FileNotFoundException{
        
        Scanner tec = new Scanner(path_d);
        
        String[] aux = tec.nextLine().split("]");
        
        pixels = Integer.parseInt(aux[0].substring(1));
        
        int person_number = 1;
        
        while(tec.hasNext()){
            
            String[] s = tec.nextLine().split("\t");
            
            Person p = new Person("Person " + person_number);
            p.createMap(s[1]);
            
            crowd.add(p);
            
            person_number++;
            
        }
        
    }
    
    public static void riseDensity(int density, int minDistance, int maxDistance){
        
        ArrayList<Person> auxCrowd = new ArrayList<>();
        int distance = maxDistance * pixels - minDistance * pixels;
        
        
        for(Person p : crowd){
            
            for(int i = 0; i < density; i++){
                
                Random r = new Random();
                int rand = r.nextInt();
                
                Person p2 = new Person("");
                Set<Integer> s = p.place.keySet();
                for(Integer key : s){
                    Integer[] array = p.place.get(key);
                    Integer[] array2 = new Integer[2];
                    array2[0] = array[0] + (rand%distance) + minDistance * pixels;
                    array2[1] = array[1] + (rand%distance) + minDistance * pixels;
                    p2.place.put(key, array2);
                }
                auxCrowd.add(p2);
                
            }
            
        }
        
        crowd.addAll(auxCrowd);
        
    }
    
    public static void collisionAvoidance(){
        
        for(int i = 0; i < crowd.size(); i++){
            
            Person p = crowd.get(i);
            Set<Integer> s = p.place.keySet();
            for(int j = i + 1; j < crowd.size(); j++){
                
                Person p2 = crowd.get(j);
                
                for(Integer key : s){
                    if(p2.place.get(key+1) != null && p.place.get(key+1) != null){
                        
                        Integer[] position = p.place.get(key+1);
                        Integer[] position2 = p2.place.get(key+1);
                        
                        if(Objects.equals(position[0], position2[0]) && Objects.equals(position[1], position2[1])){
                            
                            Integer[] array = p.place.get(key+1);
                            p.place.replace(key+1, p.place.get(key));
                            
                            for(int k = key+2; k < key+2+s.size(); k++){
                                
                                if(p.place.get(k) != null){
                                    Integer[] array2 = p.place.get(k);
                                    p.place.replace(k, array);
                                    array = array2;
                                }
                                
                            }
                            
                        }
                        
                    }
                }
                
            }
            
        }
        
    }
    
}
