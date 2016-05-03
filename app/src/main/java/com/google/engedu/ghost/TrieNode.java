package com.google.engedu.ghost;

import java.util.HashMap;

public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }



    public void add(String word) {
        HashMap<Character,TrieNode> child= children;
        for(int i=0;i<word.length();i++){
            char c=word.charAt(i);
            TrieNode t;
            if(child.containsKey(c)){
                t=child.get(c);
            }else{
                t = new TrieNode();
                child.put(c, t);
            }
            child=t.children;
            if(i== word.length()-1)
                t.isWord=true;
        }
    }
    public boolean isWord(String word) {
        TrieNode t =searchNode(word);
        if(t!=null&&t.isWord)
            return true;
        else
            return false;
    }
    public TrieNode searchNode(String s){
        HashMap<Character, TrieNode> child = children;
        TrieNode t = null;
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(child.containsKey(c)){
                t = child.get(c);
                child = t.children;
            }else{
                return null;
            }
        }
        return t;
    }



    public String getAnyWordStartingWith(String s) {
        TrieNode t = searchNode(s);
        String output = s +"";
        HashMap<Character,TrieNode> child;
        if(t==null){
            return null;
        }
        else{
            while(!t.isWord){
                child = t.children;
                Character nextKey = (Character)child.keySet().toArray()[0];
                output = output + nextKey;
                t = child.get(nextKey);
            }
        }
        return output;
    }
    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
