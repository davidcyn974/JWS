package fr.epita.assistants.presentation.rest.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class ReverseResponse {

    String original;
    String reversed;

    public ReverseResponse(String org)
    // YOU CAN SEARCH FOR YOUR OWN WAY TO REVERSE A STRING
            // THIS FUNCTION ONLY ASSIGN THIS.ORIGINAL TO ORIGINAL
            // AND REVERSE TO ORGINAL -> REVERSED
    {
        this.original = org;
        StringBuilder stringBuilder = new StringBuilder(org);
        stringBuilder.reverse();
        reversed = stringBuilder.toString();
    }
}
