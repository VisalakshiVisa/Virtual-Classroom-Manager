package CLASSROOM;

import java.util.*;

public class Main {
    private Map<String, Classroom> classrooms;

    public Main() {
        classrooms = new HashMap<>();
    }

    public void addClassroom(String name) {
        if (classrooms.containsKey(name)) {
            System.out.println("Classroom \"" + name + "\" already exists.");
        } else {
            classrooms.put(name, new Classroom(name));
            System.out.println("Classroom \"" + name + "\" has been created.");
        }
    }

    public void addStudent(String id, String name, String className) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            classroom.addStudent(new Student(id, name));
            System.out.println("Student \"" + name + "\" (ID: " + id + ") has been enrolled in \"" + className + "\".");
        } else {
            System.out.println("Classroom \"" + className + "\" does not exist. Please create the classroom first.");
        }
    }

    public void scheduleAssignment(String className, String details) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            classroom.getAssignments().add(new Assignment(details));
            System.out.println("Assignment scheduled for \"" + className + "\": " + details);
        } else {
            System.out.println("Classroom \"" + className + "\" does not exist. Please create the classroom first.");
        }
    }

    public void submitAssignment(String studentId, String className, String assignmentDetails) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            Optional<Assignment> assignmentOpt = classroom.getAssignments().stream()
                .filter(a -> a.getDetails().equals(assignmentDetails))
                .findFirst();
            if (assignmentOpt.isPresent()) {
                assignmentOpt.get().submit(studentId);
                System.out.println("Assignment submitted by Student ID " + studentId + " for \"" + className + "\".");
            } else {
                System.out.println("Assignment not found in \"" + className + "\".");
            }
        } else {
            System.out.println("Classroom \"" + className + "\" does not exist. Please create the classroom first.");
        }
    }

    public void listClassrooms() {
        if (classrooms.isEmpty()) {
            System.out.println("No classrooms available.");
        } else {
            System.out.println("Available classrooms:");
            classrooms.keySet().forEach(System.out::println);
        }
    }

    public void listStudentsInClassroom(String className) {
        Classroom classroom = classrooms.get(className);
        if (classroom != null) {
            List<Student> students = classroom.getStudents();
            if (students.isEmpty()) {
                System.out.println("No students enrolled in \"" + className + "\".");
            } else {
                System.out.println("Students in \"" + className + "\":");
                students.forEach(student -> System.out.println(student.getId() + " - " + student.getName()));
            }
        } else {
            System.out.println("Classroom \"" + className + "\" does not exist.");
        }
    }

    public static void main(String[] args) {
        Main manager = new Main();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Virtual Classroom Manager!");
        System.out.println("Commands: add_classroom, add_student, schedule_assignment, submit_assignment, list_classrooms, list_students, exit");

        while (true) {
            System.out.print("Enter command: ");
            String command = scanner.nextLine().trim().toLowerCase();

            if (command.equals("exit")) {
                System.out.println("Exiting the Virtual Classroom Manager.");
                break;
            }

            switch (command) {
                case "add_classroom":
                    System.out.print("Enter classroom name: ");
                    String className = scanner.nextLine().trim();
                    manager.addClassroom(className);
                    break;
                case "add_student":
                    System.out.print("Enter student ID: ");
                    String studentId = scanner.nextLine().trim();
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine().trim();
                    System.out.print("Enter classroom name: ");
                    className = scanner.nextLine().trim();
                    manager.addStudent(studentId, studentName, className);
                    break;
                case "schedule_assignment":
                    System.out.print("Enter classroom name: ");
                    className = scanner.nextLine().trim();
                    System.out.print("Enter assignment details: ");
                    String assignmentDetails = scanner.nextLine().trim();
                    manager.scheduleAssignment(className, assignmentDetails);
                    break;
                case "submit_assignment":
                    System.out.print("Enter student ID: ");
                    studentId = scanner.nextLine().trim();
                    System.out.print("Enter classroom name: ");
                    className = scanner.nextLine().trim();
                    System.out.print("Enter assignment details: ");
                    assignmentDetails = scanner.nextLine().trim();
                    manager.submitAssignment(studentId, className, assignmentDetails);
                    break;
                case "list_classrooms":
                    manager.listClassrooms();
                    break;
                case "list_students":
                    System.out.print("Enter classroom name: ");
                    className = scanner.nextLine().trim();
                    manager.listStudentsInClassroom(className);
                    break;
                default:
                    System.out.println("Unknown command. Please try again.");
                    System.out.println("Available commands: add_classroom, add_student, schedule_assignment, submit_assignment, list_classrooms, list_students, exit");
                    break;
            }
        }

        scanner.close();
    }
}

class Classroom {
    private String name;
    private List<Student> students;
    private List<Assignment> assignments;

    public Classroom(String name) {
        this.name = name;
        this.students = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }
}

class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Assignment {
    private String details;
    private Set<String> submittedStudents;

    public Assignment(String details) {
        this.details = details;
        this.submittedStudents = new HashSet<>();
    }

    public String getDetails() {
        return details;
    }

    public void submit(String studentId) {
        submittedStudents.add(studentId);
    }

    public Set<String> getSubmittedStudents() {
        return submittedStudents;
    }
}
