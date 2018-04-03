package com.gongyou.worker.mvp.bean;

/**
 * 作    者: ZhangLC
 * 创建时间: 2017/7/25.
 * 说    明:
 */

public class NotificationInfo {
    public String content;
    public String title;
    public String logo;
    public PayloadBean payload;

    public static class PayloadBean {
        public String relation_id;
        public int type;

        @Override
        public String toString() {
            return "PayloadBean{" +
                    "relation_id='" + relation_id + '\'' +
                    ", type=" + type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NotificationInfo{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", logo='" + logo + '\'' +
                ", payload=" + payload +
                '}';
    }
}
