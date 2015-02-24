package com.yanp.traficmetro.data;

import com.yanp.traficmetro.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by YPierru on 21/02/2015.
 */
public class Struct_AllComments {

    /** Instance unique pré-initialisée */
    private static Struct_AllComments INSTANCE = new Struct_AllComments();

    private HashMap<Integer,ArrayList<TL_Comment>> allCom = new HashMap<Integer,ArrayList<TL_Comment>>();

    /** Constructeur privé */
    private Struct_AllComments(){
        /**
         * Récupère depuis la base de données distantes les différentes TL pour chaque station
         */

        for(int i=1;i<80;i++){
            ArrayList<TL_Comment> list = new ArrayList<TL_Comment>();
            list.add(0,new TL_Comment(Constants.COLOR_TRAFICLIGHT_RED, "17h40"));
            list.add(0,new TL_Comment(Constants.COLOR_TRAFICLIGHT_GREEN, "17h40"));
            list.add(0,new TL_Comment(Constants.COLOR_TRAFICLIGHT_ORANGE, "17h40"));
            list.add(0,new TL_Comment(Constants.COLOR_TRAFICLIGHT_BLACK, "17h40"));
            this.allCom.put(i,list);
        }
    }

    /** Point d'accès pour l'instance unique du singleton */
    public static Struct_AllComments getInstance(){
        return INSTANCE;
    }


    public ArrayList<TL_Comment> getListTLCForStation(int id){
        return this.allCom.get(id);
    }

    public int getLastColorById(int id){
        ArrayList<TL_Comment> listeTLC = this.allCom.get(id);
        return listeTLC.get(0).getColor();
    }


}
