package me.suhyuk.spring.board.entities;

public class ResponseMessage {
    private String text;

    public ResponseMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
