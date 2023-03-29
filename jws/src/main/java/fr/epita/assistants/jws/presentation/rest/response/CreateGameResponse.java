package fr.epita.assistants.jws.presentation.rest.response;

import fr.epita.assistants.jws.converter.PlayerInfo;
import fr.epita.assistants.jws.domain.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class  CreateGameResponse{
    public Timestamp startTime;
    public String state;
    //public List<PlayerEntity> players;
    public List<PlayerInfo> players;
    public List<String> map;
    public long id;
}