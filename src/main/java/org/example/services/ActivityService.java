package org.example.services;

import org.example.entities.Activity;
import org.example.entities.Student;
import org.example.interfaces.Repository;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class ActivityService extends BaseService implements Repository<Activity> {

    public ActivityService() {
        super();
    }
    @Override
    public boolean create(Activity o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Activity o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Activity o) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Activity findById(int id) {
        Activity activity = null;
        session = sessionFactory.openSession();
        activity = (Activity) session.get(Activity.class, id);
        session.close();
        return activity;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activityList = null;
        session = sessionFactory.openSession();
        Query<Activity> activityQuery = session.createQuery("from Activity");
        activityList = activityQuery.list();
        session.close();
        return activityList;
    }

    public boolean addStudentToActivity(int studentId, int activityId){
        session = sessionFactory.openSession();
        session.beginTransaction();

        Activity activity = (Activity) session.get(Activity.class, activityId);
        Student student = (Student) session.get(Student.class, studentId);

        activity.getStudents().add(student);
        student.getActivities().add(activity);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public boolean removeStudentFromActivity(int studentId, int activityId) {
        session = sessionFactory.openSession();
        session.beginTransaction();

        Activity activity = (Activity) session.get(Activity.class, activityId);
        Student student = (Student) session.get(Student.class, studentId);

        activity.getStudents().remove(student);
        student.getActivities().remove(activity);

        session.getTransaction().commit();
        session.close();
        return true;
    }

    public List<Activity> filterActivitiesByDate(Date startDate, Date endDate){
        List<Activity> activityList = null;
        session = sessionFactory.openSession();
        Query<Activity> activityQuery = session.createQuery("from Activity where date > :startDate and date < :endDate");
        activityQuery.setParameter("startDate", startDate);
        activityQuery.setParameter("endDate", endDate);
        activityList = activityQuery.list();
        session.close();
        return activityList;
    }

    public void begin(){
        session = sessionFactory.openSession();
    }

    public void end(){
        sessionFactory.close();
    }
}
