package fr.epita.assistants.presentation.rest.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor // YOU CAN DO YOUR OWN CONSTRUCTOR INSTEAD OF DOING THIS
@Getter // SAME FOR GETTER YOU CAN CREATE YOUR OWN GETTER AS IN HELLO RESPONSE CLASS.JAVA
public class ReverseRequest {

    String content;

}
