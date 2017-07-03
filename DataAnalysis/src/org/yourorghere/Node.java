/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yourorghere;

import java.util.HashMap;

/**
 *
 * @author user
 */
public class Node{
        
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
