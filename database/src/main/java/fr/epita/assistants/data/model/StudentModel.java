package fr.epita.assistants.data.model;

import javax.persistence.*;


@Entity @Table(name = "student_model")
public class StudentModel {
    @Column(name = "name")
    public String name;

    @Column(name = "course_id")
    public long course_id;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;
}
