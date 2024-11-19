package com.ssafy.snuggle.model.dto;

public class Tagging {
    private String taggingId;      // 태깅 ID (Primary Key)
    private String videoSrc;    // 비디오 경로
    private String videoTitle;  // 비디오 제목
    private String videoContent;// 비디오 내용

    // 기본 생성자
    public Tagging() {}

    // 매개변수 생성자
    public Tagging(String taggingId, String videoSrc, String videoTitle, String videoContent) {
        this.taggingId = taggingId;
        this.videoSrc = videoSrc;
        this.videoTitle = videoTitle;
        this.videoContent = videoContent;
    }

    public String getTaggingId() {
        return taggingId;
    }

    public void setTaggingId(String taggingId) {
        this.taggingId = taggingId;
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoContent() {
        return videoContent;
    }

    public void setVideoContent(String videoContent) {
        this.videoContent = videoContent;
    }

    // toString() method for debugging
    @Override
    public String toString() {
        return "Tagging{" +
                "taggingId=" + taggingId +
                ", videoSrc='" + videoSrc + '\'' +
                ", videoTitle='" + videoTitle + '\'' +
                ", videoContent='" + videoContent + '\'' +
                '}';
    }
}
