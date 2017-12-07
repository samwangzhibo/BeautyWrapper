package com.samnie.beautypic.entry;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by samwangzhibo on 2017/10/26.
 */

public class BeautyList implements Serializable{
    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    public PageBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PageBean pagebean) {
        this.pagebean = pagebean;
    }

    int ret_code;
    PageBean pagebean;
    public class PageBean implements Serializable{
        public int allPages;
        public ArrayList<Content> contentlist;
        public int currentPage;
        public int allNum;
        public int maxResult;

        public class Content implements Serializable{
            public String typeName;
            public String title;
            public ArrayList<Picture> list;
            public String itemId;
            public int type;
            public String ct;

            public class Picture implements Serializable{
                public String big;
                public String small;
                public String middle;
            }
        }
    }
}
