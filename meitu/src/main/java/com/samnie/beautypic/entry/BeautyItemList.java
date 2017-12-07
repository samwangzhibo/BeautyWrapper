package com.samnie.beautypic.entry;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by samwangzhibo on 2017/10/26.
 */

public class BeautyItemList implements Serializable{
    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public ArrayList<BeautyItem> getList() {
        return list;
    }

    public void setList(ArrayList<BeautyItem> list) {
        this.list = list;
    }

    int ret_code;
    ArrayList<BeautyItem> list;
    public class BeautyItem implements Serializable{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<BeautyItemEntry> getList() {
            return list;
        }

        public void setList(ArrayList<BeautyItemEntry> list) {
            this.list = list;
        }

        String name;
        ArrayList<BeautyItemEntry> list;
        public class BeautyItemEntry implements Serializable{
            int id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            String name;
        }
    }
}
