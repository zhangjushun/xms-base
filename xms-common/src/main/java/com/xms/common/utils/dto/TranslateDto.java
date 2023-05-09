package com.xms.common.utils.dto;

public class TranslateDto {
    private String text;
    private String from;
    private String to;
    private String textType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }
}
