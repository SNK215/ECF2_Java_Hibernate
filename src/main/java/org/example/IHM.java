package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.example.entities.Activity;
import org.example.entities.Student;
import org.example.services.ActivityService;
import org.example.services.StudentService;

public class IHM {
    private StudentService studentService;
    private ActivityService activityService;
    private Scanner scanner;
    private String choice;

    public IHM() {
        studentService = new StudentService();
        activityService = new ActivityService();
        scanner = new Scanner(System.in);
    }

    public void start(){
        do {
            menu();
            choice = scanner.nextLine();
            switch (choice) {
                case "1" :
                    displayStudentById();
                    break;
                case "2" :
                    displayAllStudents();
                    break;
                case "3" :
                    addStudent();
                    break;
                case "4" :
                    updateStudent();
                    break;
                case "5" :
                    deleteStudent();
                    break;
                case "6" :
                    addActivity();
                    break;
                case "7" :
                    displayActivityById();
                    break;
                case "8" :
                    displayAllActivities();
                    break;
                case "9" :
                    updateActivity();
                    break;
                case "10" :
                    deleteActivity();
                    break;
                case "11" :
                    addStudentToActivity();
                    break;
                case "12" :
                    removeStudentFromActivity();
                    break;
                case "13" :
                    filterActivitiesByDate();
                    break;
            }
        } while(!choice.equals("0"));
        studentService.end();
        activityService.end();
    }

    public void menu(){
        System.out.println("####Gestion centre sportif####");
        System.out.println("1- Afficher un étudiant et ses activités");
        System.out.println("2- Afficher tous les étudiants");
        System.out.println("3- Ajouter un étudiant");
        System.out.println("4- Mettre à jour un élève");
        System.out.println("5- Supprimer un étudiant");
        System.out.println("6- Ajouter une activité");
        System.out.println("7- Afficher une activité avec ses étudiants");
        System.out.println("8- Afficher toutes les activités");
        System.out.println("9- Mettre à jour une activité");
        System.out.println("10- Supprimer une activité");
        System.out.println("11- Ajouter un étudiant à une activité");
        System.out.println("12- Supprimer un étudiant d'une activité");
        System.out.println("13- Filtrer les activités par date");
        System.out.println("0- Quitter l'application");
    }

    public boolean displayStudentById(){
        System.out.println("Entrez l'Id de l'élève: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Student student = studentService.findById(id);
        if (student != null) {
            System.out.println(student);
            System.out.println("* Activité de l'étudiant : ");
            for (Activity a : student.getActivities()) {
                System.out.println(a);
            }
            return true;
        } else {
            System.out.println("* Aucun étudiant trouvé *");
            return false;
        }
    }

    public void displayAllStudents(){
        List<Student> students = studentService.findAll();
        if (!students.isEmpty()) {
            for (Student s : students) {
                System.out.println(s);
            }
        } else {
            System.out.println("* Aucun étudiant enregistré *");
        }
    }

    public boolean addStudent(){
        System.out.println("Entrez le prénom : ");
        String firstname = scanner.nextLine();
        System.out.println("Entrez le nom : ");
        String lastname = scanner.nextLine();
        System.out.println("Entrez l'âge : ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(firstname,lastname,age);

        if (studentService.create(student)) {
            System.out.println("* Étudiant ajouté ! *");
            return true;
        } else {
            System.out.println("* Impossible d'ajouter l'étudiant *");
            return false;
        }
    }

    public boolean updateStudent(){
        System.out.println("Entrez l'Id de l'étudiant à mettre à jour : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Student student = studentService.findById(id);

        do {
            System.out.println("Aucun étudiant trouvé, veuillez entrez à nouveau l'Id : ");
            id = scanner.nextInt();
            scanner.nextLine();
            student = studentService.findById(id);
        } while (student == null);

        System.out.println("Entrez le nouveau prénom : ");
        String firstname = scanner.nextLine();
        System.out.println("Entrez le nouveau nom : ");
        String lastname = scanner.nextLine();
        System.out.println("Entrez le nouvel âge : ");
        int age = scanner.nextInt();
        scanner.nextLine();

        student.setFirstname(firstname);
        student.setLastname(lastname);
        student.setAge(age);

        if (studentService.update(student)) {
            System.out.println("* Étudiant mis à jour ! *");
            return true;
        } else {
            System.out.println("* Impossible de mettre à jour l'étudiant *");
            return false;
        }
    }

    public boolean deleteStudent(){
        System.out.println("Entrez l'Id de l'étudiant à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Student student = studentService.findById(id);

        // On retire l'étudiant de chacune de ses activités avant de le supprimer
        for (Activity a : student.getActivities()) {
            activityService.removeStudentFromActivity(id,a.getId());
        }

        if (student == null) {
            System.out.println("* Aucun étudiant trouvé *");
            return false;
        } else {
            if (studentService.delete(student)) {
                System.out.println("* Étudiant supprimé ! *");
                return true;
            } else {
                System.out.println("* Impossible de supprimer l'étudiant *");
                return false;
            }
        }
    }

    public boolean addActivity(){
        try {
            System.out.println("Entrez le nom de l'activité : ");
            String name = scanner.nextLine();
            System.out.println("Entrez la date de l'activité (dd/MM/yyyy) : ");
            String dateString = scanner.nextLine();

            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
            Activity activity = new Activity(name,date);

            if (activityService.create(activity)) {
                System.out.println("* Activité ajoutée ! *");
                return true;
            } else {
                System.out.println("* Impossible d'ajouter l'activité *");
                return false;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean displayActivityById(){
        System.out.println("Entrez l'Id de l'activité : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Activity activity = activityService.findById(id);

        if (activity == null) {
            System.out.println("* Aucune activité trouvée *");
            return false;
        } else {
            System.out.println(activity);
            if (activity.getStudents().isEmpty()) {
                System.out.println("* Aucun participant pour cette activité *");
            } else {
                System.out.println("* Participants :");
                for (Student s: activity.getStudents()) {
                    System.out.println(s);
                }
            }
            return true;
        }
    }

    public void displayAllActivities(){
        List<Activity> activities = activityService.findAll();
        if (!activities.isEmpty()) {
            for (Activity a: activities) {
                System.out.println(a);
            }
        } else {
            System.out.println("* Aucune activité enregistrée *");
        }

    }

    public boolean updateActivity(){
        try {
            System.out.println("Entrez l'Id de l'activité à mettre à jour : ");
            int id = scanner.nextInt();
            scanner.nextLine();
            Activity activity = activityService.findById(id);

            do {
                System.out.println("Aucune activité trouvée, veuillez entrer à nouveau l'Id : ");
                id = scanner.nextInt();
                scanner.nextLine();
                activity = activityService.findById(id);
            } while (activity == null);

            System.out.println("Entrez le nouveau nom de l'activité : ");
            String name = scanner.nextLine();
            System.out.println("Entrez la nouvelle date (dd/MM/yyyy) : ");
            String dateString = scanner.nextLine();
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);

            activity.setName(name);
            activity.setDate(date);

            if (activityService.update(activity)) {
                System.out.println("* Activité mise à jour ! *");
                return true;
            } else {
                System.out.println("* Impossible de mettre à jour l'activité *");
                return false;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean deleteActivity(){
        System.out.println("Entrez l'Id de l'activité à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Activity activity = activityService.findById(id);

        if (activity == null) {
            System.out.println("* Aucune activité trouvée *");
            return false;
        } else {
            if (activityService.delete(activity)) {
                System.out.println("* Activité supprimée ! *");
                return true;
            } else {
                System.out.println("* Impossible de supprimer l'activité *");
                return false;
            }
        }
    }

    public boolean addStudentToActivity(){
        System.out.println("Entrez l'Id de l'étudiant : ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez l'Id de l'activité : ");
        int activityId = scanner.nextInt();
        scanner.nextLine();

        if (activityService.addStudentToActivity(studentId, activityId)) {
            System.out.println("* Ajout effectué ! *");
            return true;
        } else {
            System.out.println("* Impossible d'ajouter l'étudiant à l'activité *");
            return false;
        }
    }

    public boolean removeStudentFromActivity(){
        System.out.println("Entrez l'Id de l'étudiant : ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Entrez l'Id de l'activité : ");
        int activityId = scanner.nextInt();
        scanner.nextLine();

        if (activityService.removeStudentFromActivity(studentId, activityId)) {
            System.out.println("* Étudiant retiré de l'activité ! *");
            return true;
        } else {
            System.out.println("* Impossible de retirer l'étudiant de l'activité *");
            return false;
        }
    }

    public boolean filterActivitiesByDate(){
        try {
            System.out.println("Entrez la date de début (dd/MM/yyyy) : ");
            String startDateString = scanner.nextLine();
            Date startDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDateString);

            System.out.println("Entrez la date de fin (dd/MM/yyyy) : ");
            String endDateString = scanner.nextLine();
            Date endDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDateString);

            List<Activity> activities = activityService.filterActivitiesByDate(startDate, endDate);

            if (!activities.isEmpty()) {
                for (Activity a : activities) {
                    System.out.println(a);
                }
                return true;
            } else {
                System.out.println("* Aucune activité trouvée *");
                return false;
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }
}
