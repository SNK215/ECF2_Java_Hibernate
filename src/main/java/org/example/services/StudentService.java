package org.example.services;

import org.example.entities.Student;
import org.example.interfaces.Repository;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Date;

public class StudentService extends BaseService implements Repository<Student> {

    public StudentService(){
        super();
    }
    @Override
    public boolean create(Student o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Student o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Student findById(int id) {
        Student student = null;
        session = sessionFactory.openSession();
        student = (Student) session.get(Student.class, id);
        session.close();
        return student;
    }

    @Override
    public List<Student> findAll() {
        List<Student> studentList = null;
        session = sessionFactory.openSession();
        Query<Student> studentQuery = session.createQuery("from Student");
        studentList = studentQuery.list();
        session.close();
        return studentList;
    }

    public void begin(){
        session = sessionFactory.openSession();
    }
    public void end(){
        sessionFactory.close();
    }
}
