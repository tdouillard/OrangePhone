package com.example.douille.contactphone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by douille on 12/8/16.
 */

public class NestedArrayListHelper implements Serializable {
    private HashMap<String,ArrayList<String>> groupList;

    public NestedArrayListHelper(HashMap<String,ArrayList<String>> groupList) {
        this.groupList = groupList;
    }

    public HashMap<String,ArrayList<String>> getList() {
        return this.groupList;
    }
//    private ArrayList<ArrayList<String>> arrayLists;
//
//    public NestedArrayListHelper(ArrayList<ArrayList<String>> arrayLists) {
//        this.arrayLists = arrayLists;
//    }
//
//    public ArrayList<ArrayList<String>> getList() {
//        return this.arrayLists;
//    }
}
