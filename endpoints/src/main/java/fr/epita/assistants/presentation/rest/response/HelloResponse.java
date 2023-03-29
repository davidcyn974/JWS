package fr.epita.assistants.presentation.rest.response;


import lombok.Getter;
//@Getter
public class HelloResponse {

    static String content;

    public HelloResponse(String content) {
        this.content = "hello " + content;
    }


    public Object getContent() {
        return content;
    }

}
