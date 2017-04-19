/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
public class Events {
    
    private ArrayList<Node> crowd;
    private int pixels;
    
    public Events(){
        crowd = new ArrayList<Node>();
    }
    
    private class Node{
        
        String person;
        HashMap<Integer, Integer[]> place;
        boolean flag;
        
        public Node(String newPerson){
            
            person = newPerson;
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
        
        public String getPerson(){ return person; }
        public HashMap<Integer, Integer[]> getMap(){ return place; }
        public boolean getFlag(){ return flag; }
        
        public void setFlag(boolean newFlag){ flag = newFlag; }
        
    }
    
    public void createMaps(File path_d) throws FileNotFoundException{
        
        Scanner tec = new Scanner(path_d);
        
        String[] aux = tec.nextLine().split("]");
        
        pixels = Integer.parseInt(aux[0].substring(1));
        
        int person_number = 1;
        
        while(tec.hasNext()){
            
            String[] s = tec.nextLine().split("\t");
            
            Node n = new Node("Person " + person_number);
            n.createMap(s[1]);
            
            crowd.add(n);
            
            person_number++;
            
        }
        
        //for(int i = 0; i < crowd.size(); i++){
            //System.out.println(crowd.get(i).getPerson());
        //}
        
    }
    
    public ArrayList<String> searchEncounter(){
        
        
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < crowd.size(); i++) {

            Node n = crowd.get(i);

            for (int j = 0; j < crowd.size(); j++) {

                Node m = crowd.get(j);
                int order = n.getMap().size();
                int index = 1;
                int count_time = 0;
                int n_x = 0;
                int n_y = 0;
                int m_x = 0;
                int m_y = 0;

                if(!n.getPerson().equals(m.getPerson())){
                while (index < order) {

                    if (m.getMap().containsKey(index) && n.getMap().containsKey(index)) {

                        Integer[] position_n = n.getMap().get(index);
                        Integer[] position_m = m.getMap().get(index);
                        int x = position_n[0] - position_m[0];
                        int y = position_n[1] - position_m[1];

                        if (count_time == 0) {

                            if (position_n[0] == position_m[0]) {
                                if (Math.abs(position_n[1] - position_m[1]) <= pixels) {
                                    count_time++;
                                    n_x = position_n[0];
                                    n_y = position_n[1];
                                    m_x = position_m[0];
                                    m_y = position_m[1];
                                }
                            }else
                            if (position_n[1] == position_m[1]) {
                                if (Math.abs(position_n[0] - position_m[0]) <= pixels) {
                                    count_time++;
                                    n_x = position_n[0];
                                    n_y = position_n[1];
                                    m_x = position_m[0];
                                    m_y = position_m[1];
                                }
                            }else

                            if ((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(pixels, 2)) {
                                count_time++;
                                n_x = position_n[0];
                                n_y = position_n[1];
                                m_x = position_m[0];
                                m_y = position_m[1];
                            }

                        } else if (count_time < 3) {

                            if (n_x == position_n[0] && n_y == position_n[1] && m_x == position_m[0] && m_y == position_m[1]) {
                                count_time++;
                            } else {
                                count_time = 0;
                            }

                        } else if (count_time >= 3) {

                            if (n_x == position_n[0] && n_y == position_n[1] && m_x == position_m[0] && m_y == position_m[1]) {
                                count_time++;
                            } else {

                                result.add("Os indiv?duos " + n.getPerson() + " e " + m.getPerson() + " se encontraram entre " + (index - 1 - count_time) + " e " + (index - 1));

                            }

                        }

                    }

                    index++;
                }

            }
            }

        }

        return result;

    }
    
    public ArrayList<String> searchGroup() {

        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Node> crowd_aux = new ArrayList<Node>();

        for (Node n : crowd) {

            Iterator order = n.getMap().keySet().iterator();

            n.setFlag(true);

            while (order.hasNext()) {

                Integer index = (Integer) order.next();

                String group = "";

                for (Node m : crowd) {

                    if (m.getMap().containsKey(index) && !n.getPerson().equals(m.getPerson())) {

                        Integer[] position_n = n.getMap().get(index);
                        Integer[] position_m = m.getMap().get(index);
                        int x = position_n[0] - position_m[0];
                        int y = position_n[1] - position_m[1];

                        if ((position_n[0] == position_m[0]) && (m.getFlag() == false)) {
                            if (Math.abs(position_n[1] - position_m[1]) <= pixels) {
                                crowd_aux.add(m);
                                group = group + m.getPerson();
                            }
                        } else {
                            if ((position_n[1] == position_m[1]) && (m.getFlag() == false)) {
                                if (Math.abs(position_n[0] - position_m[0]) <= pixels) {
                                    crowd_aux.add(m);
                                    group = group + m.getPerson();
                                }
                            } else {
                                if (((Math.pow(x, 2) + Math.pow(y, 2)) <= Math.pow(pixels, 2)) && (m.getFlag() == false)) {
                                    crowd_aux.add(m);
                                    group = group + m.getPerson();
                                }
                            }
                        }

                    }

                }

                if (!group.equals("")) {
                    boolean flag = true;
                    int k = 0;
                    String u = "";
                    for(String s : result){
                        if(s.contains(n.getPerson()) && s.contains(index + ".0")) {
                            u = group + " " + s;
                            k = result.indexOf(s);
                            flag = false;
                        }
                    }
                    if(flag) result.add(n.getPerson() + " " + group + " no tempo " + index + ".0");
                    else {
                        result.remove(k);
                        result.add(u);
                    }
                }
            }

        }

        resetFlags();

        return result;

    }
    
    public void resetFlags(){
        
        for(Node n : crowd){
            n.setFlag(false);
        }
        
    }
    
    public ArrayList<String> searchApproximation(){
        
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < crowd.size(); i++) {

            //System.out.println(crowd.get(i).getPerson());
            Node n = crowd.get(i);
            //crowd_aux.remove(n);
            String ap = "";

            for (int j = 0; j < crowd.size(); j++) {

                Node m = crowd.get(j);
                int order = n.getMap().size();
                int time = 1;
                double range = 0;

                if(!n.getPerson().equals(m.getPerson())){
                while (time <= order) {

                    if (m.getMap().containsKey(time) && n.getMap().containsKey(time)) {

                        Integer[] position_n = n.getMap().get(time);
                        Integer[] position_m = m.getMap().get(time);
                        
                        int x = position_n[0] - position_m[0];
                        int y = position_n[1] - position_m[1];
                        
                        if(time == 1) range = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));

                        if (position_n[0] == position_m[0]) {
                            if (Math.abs(position_n[1] - position_m[1]) < range) {
                                ap = ap + " " + time;
                                range = Math.abs(position_n[1] - position_m[1]);
                            }
                        } else
                        if (position_n[1] == position_m[1]) {
                            if (Math.abs(position_n[0] - position_m[0]) < range) {
                                ap = ap + " " + time;
                                range = Math.abs(position_n[0] - position_m[0]);
                            }
                        } else
                        if ((Math.pow(x, 2) + Math.pow(y, 2)) < Math.pow(range, 2)) {
                            ap = ap + " " + time;
                            range = Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
                        }

                    }
                    
                    time++;

                }
                
                result.add(n.getPerson() + " e " + m.getPerson() + " se aproximam no(s) tempo(s):" + ap);
                ap = "";
                }
            }

        }
        
        return result;
        
    }
    
    public String Megumin(int time, double range){
        
        return "";
        
    }
    
}