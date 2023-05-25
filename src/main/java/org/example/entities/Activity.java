package org.example.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "studying", joinColumns = @JoinColumn(name = "activity_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;

    public Activity() {
    }

    public Activity(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String toString(){
        return "* Activité "+id+" : "+name+", "+date;
    }
}
