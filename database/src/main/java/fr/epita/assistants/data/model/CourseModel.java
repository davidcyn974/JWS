package fr.epita.assistants.data.model;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "course_model")
public class CourseModel {

    @Column(name = "name")
    public String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;


    public
    @ElementCollection
    @CollectionTable(name = "course_model_tags", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "tag")
    List<String> course_model_tags;

}
