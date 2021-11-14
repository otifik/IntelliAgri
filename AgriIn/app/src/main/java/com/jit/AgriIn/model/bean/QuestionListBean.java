package com.jit.AgriIn.model.bean;

import java.util.List;

public class QuestionListBean {

    /**
     * id : 2
     * username : user
     * userImage : null
     * userType : 0
     * type : 1
     * description : 茄子根结线虫病的症状
     * image : null
     * publishTime : 2019-05-31 14:06:18
     * answerNum : 2
     * answers : [{"id":3,"question_id":2,"content":"茄子根结线虫病主要为害根部。病部产生大小不一，形状不定的肥肿、畸形瘤状结。剖开根结有乳白色线虫，多在根结上部产生新根，再侵染后又形成根结状肿瘤。发病轻时，地上部症状不明显，发病严重时植株矮小，发育不良，叶片变黄，结果小。高温干旱时病株出现萎蔫或提前枯死。","username":"expert","publishTime":"2019-05-31 14:07:37","userImage":null,"userType":1},{"id":4,"question_id":2,"content":"茄子根结线虫病主要为害根部。病部产生大小不一，形状不定的肥肿、畸形瘤状结。剖开根结有乳白色线虫，多在根结上部产生新根，再侵染后又形成根结状肿瘤。发病轻时，地上部症状不明显，发病严重时植株矮小，发育不良，叶片变黄，结果小。高温干旱时病株出现萎蔫或提前枯死。","username":"ypq123","publishTime":"2019-05-16 14:33:32","userImage":"http://223.2.197.246:8088/planting/image/a4d1965a-0194-4f6d-8b12-b5645a56092c.png","userType":0}]
     */

    private int id;
    private String username;
    private String userImage;
    private int userType;
    private int type;
    private String description;
    private String image;
    private String publishTime;
    private int answerNum;
    private List<AnswersBean> answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(int answerNum) {
        this.answerNum = answerNum;
    }

    public List<AnswersBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersBean> answers) {
        this.answers = answers;
    }

    public static class AnswersBean {
        /**
         * id : 3
         * question_id : 2
         * content : 茄子根结线虫病主要为害根部。病部产生大小不一，形状不定的肥肿、畸形瘤状结。剖开根结有乳白色线虫，多在根结上部产生新根，再侵染后又形成根结状肿瘤。发病轻时，地上部症状不明显，发病严重时植株矮小，发育不良，叶片变黄，结果小。高温干旱时病株出现萎蔫或提前枯死。
         * username : expert
         * publishTime : 2019-05-31 14:07:37
         * userImage : null
         * userType : 1
         */

        private int id;
        private int question_id;
        private String content;
        private String username;
        private String publishTime;
        private String userImage;
        private int userType;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getQuestion_id() {
            return question_id;
        }

        public void setQuestion_id(int question_id) {
            this.question_id = question_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
