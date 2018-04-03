package com.gongyou.worker.mvp.bean.first;

import java.util.List;

/**
 * 作    者: ZhangLC
 * 创建时间: 2017/7/11.
 * 说    明:
 */

public class ListFilterInfo {
    public List<TopBean> top;
    public List<NormalBean> normal;

    public static class TopBean {
        public int p_id;
        public int istop;
        public String work_province;
        public String work_city;
        public String work_district;
        public String work_address;
        public int isrecommend;
        public String map_lat;
        public String map_lng;
        public String wage;
        public String head_image;
        public String age;
        public String education;
        public String profession_name;
        public String unitname;
        public String a_id;
        public String distance;
        public String work_year;

        @Override
        public String toString() {
            return "TopBean{" +
                    "p_id=" + p_id +
                    ", istop=" + istop +
                    ", work_province='" + work_province + '\'' +
                    ", work_city='" + work_city + '\'' +
                    ", work_district='" + work_district + '\'' +
                    ", work_address='" + work_address + '\'' +
                    ", isrecommend=" + isrecommend +
                    ", map_lat='" + map_lat + '\'' +
                    ", map_lng='" + map_lng + '\'' +
                    ", wage='" + wage + '\'' +
                    ", head_image='" + head_image + '\'' +
                    ", age=" + age +
                    ", education='" + education + '\'' +
                    ", profession_name='" + profession_name + '\'' +
                    ", unitname='" + unitname + '\'' +
                    ", a_id=" + a_id +
                    ", distance='" + distance + '\'' +
                    ", work_year='" + work_year + '\'' +
                    '}';
        }
    }

    public static class NormalBean {
        public int p_id;
        public String work_province;
        public String work_city;
        public String work_district;
        public String work_address;
        public int isrecommend;
        public int istop;
        public String map_lat;
        public String map_lng;
        public String wage;
        public String head_image;
        public int age;
        public String education;
        public String profession_name;
        public String unitname;
        public int a_id;
        public String distance;
        public String work_year;

        @Override
        public String toString() {
            return "NormalBean{" +
                    "p_id=" + p_id +
                    ", work_province='" + work_province + '\'' +
                    ", work_city='" + work_city + '\'' +
                    ", work_district='" + work_district + '\'' +
                    ", work_address='" + work_address + '\'' +
                    ", isrecommend=" + isrecommend +
                    ", istop=" + istop +
                    ", map_lat='" + map_lat + '\'' +
                    ", map_lng='" + map_lng + '\'' +
                    ", wage='" + wage + '\'' +
                    ", head_image='" + head_image + '\'' +
                    ", age=" + age +
                    ", education='" + education + '\'' +
                    ", profession_name='" + profession_name + '\'' +
                    ", unitname='" + unitname + '\'' +
                    ", a_id=" + a_id +
                    ", distance='" + distance + '\'' +
                    ", work_year='" + work_year + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ListFilterInfo{" +
                "top=" + top +
                ", normal=" + normal +
                '}';
    }

    //    public List<TopBean> top;
//    public List<NormalBean> normal;
//
//    public static class TopBean {
//        public int p_id;
//        public String type_mark;
//        public int expire_time;
//        public String work_province;
//        public String work_city;
//        public String work_district;
//        public int istop;
//        public String work_address;
//        public String project_name;
//        public int ishot;
//        public int isrecommend;
//        public String map_lat;
//        public String map_lng;
//        public String wage;
//        public int need;
//        public String profession_name;
//        public String unitname;
//        public int enterprise_id;
//        public String enterprisename;
//        public String rate;
//        public String vv_logo;
//        public String workyear;
//        public String distance;
//        public int has_men;
//        public String age;
//        public String education;
//
//        @Override
//        public String toString() {
//            return "TopBean{" +
//                    "p_id=" + p_id +
//                    ", type_mark='" + type_mark + '\'' +
//                    ", expire_time=" + expire_time +
//                    ", work_province='" + work_province + '\'' +
//                    ", work_city='" + work_city + '\'' +
//                    ", work_district='" + work_district + '\'' +
//                    ", istop=" + istop +
//                    ", work_address='" + work_address + '\'' +
//                    ", project_name='" + project_name + '\'' +
//                    ", ishot=" + ishot +
//                    ", isrecommend=" + isrecommend +
//                    ", map_lat='" + map_lat + '\'' +
//                    ", map_lng='" + map_lng + '\'' +
//                    ", wage='" + wage + '\'' +
//                    ", need=" + need +
//                    ", profession_name='" + profession_name + '\'' +
//                    ", unitname='" + unitname + '\'' +
//                    ", enterprise_id=" + enterprise_id +
//                    ", enterprisename='" + enterprisename + '\'' +
//                    ", rate='" + rate + '\'' +
//                    ", vv_logo='" + vv_logo + '\'' +
//                    ", workyear='" + workyear + '\'' +
//                    ", distance='" + distance + '\'' +
//                    ", has_men=" + has_men +
//                    ", age='" + age + '\'' +
//                    ", education='" + education + '\'' +
//                    '}';
//        }
//    }
//
//    public static class NormalBean {
//        public int p_id;
//        public String work_province;
//        public String work_city;
//        public String work_district;
//        public String work_address;
//        public int isrecommend;
//        public int istop;
//        public String map_lat;
//        public String map_lng;
//        public String wage;
//        public String head_image;
//        public int age;
//        public String education;
//        public String profession_name;
//        public String unitname;
//        public int a_id;
//        public String distance;
//        public String work_year;
//
//        @Override
//        public String toString() {
//            return "NormalBean{" +
//                    "p_id=" + p_id +
//                    ", work_province='" + work_province + '\'' +
//                    ", work_city='" + work_city + '\'' +
//                    ", work_district='" + work_district + '\'' +
//                    ", work_address='" + work_address + '\'' +
//                    ", isrecommend=" + isrecommend +
//                    ", istop=" + istop +
//                    ", map_lat='" + map_lat + '\'' +
//                    ", map_lng='" + map_lng + '\'' +
//                    ", wage='" + wage + '\'' +
//                    ", head_image='" + head_image + '\'' +
//                    ", age=" + age +
//                    ", education='" + education + '\'' +
//                    ", profession_name='" + profession_name + '\'' +
//                    ", unitname='" + unitname + '\'' +
//                    ", a_id=" + a_id +
//                    ", distance='" + distance + '\'' +
//                    ", work_year='" + work_year + '\'' +
//                    '}';
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "ListFilterInfo{" +
//                "top=" + top +
//                ", normal=" + normal +
//                '}';
//    }

    //    public int p_id;
//    public String work_province;
//    public String work_city;
//    public String work_district;
//    public String work_address;
//    public int isrecommend;
//    public int istop;
//    public String map_lat;
//    public String map_lng;
//    public String wage;
//    public String head_image;
//    public int age;
//    public String education;
//    public String profession_name;
//    public String unitname;
//    public int a_id;
//    public String distance;
//    public String work_year;
}
