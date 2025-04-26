import model.Student;
import model.StudentCourse;
import model.User;
import model.Course;
import service.StudentService;
import service.AdminService;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        boolean running = true;

        while (running) {
            System.out.println("===========================");
            System.out.println("🎓 学生选课管理系统");
            System.out.println("===========================");
            System.out.println("1. 登录");
            System.out.println("2. 注册");
            System.out.println("3. 退出");
            System.out.print("请选择操作（输入 1-3）：");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(scanner, userService);
                    break;
                case 2:
                    register(scanner, userService);
                    break;
                case 3:
                    running = false;
                    break;
            }
        }

        System.out.println("系统已退出。");
    }

    private static void register(Scanner scanner, UserService userService) {
        System.out.println("===== 用户注册 =====");
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();
        System.out.print("请确认密码：");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("两次输入的密码不一致！");
            return;
        }

        System.out.print("请选择角色（输入 1 代表学生，2 代表管理员）：");
        int role = scanner.nextInt();
        scanner.nextLine();

        if (userService.register(username, password, role)) {
            System.out.println("注册成功！请返回主界面登录。");
        } else {
            System.out.println("注册失败！");
        }
    }

    private static void login(Scanner scanner, UserService userService) {
        System.out.println("===== 用户登录 =====");
        System.out.print("请输入用户名：");
        String username = scanner.nextLine();
        System.out.print("请输入密码：");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user == null) {
            System.out.println("用户名或密码错误！");
            return;
        }

        System.out.println("登录成功！你的角色是：" + (user.getRole() == 1 ? "学生" : "管理员"));
        if (user.getRole() == 1) {
            studentMenu(scanner, user);
        } else {
            adminMenu(scanner, user);
        }
    }

    private static void studentMenu(Scanner scanner, User user) {
        StudentService studentService = new StudentService();
        boolean running = true;

        while (running) {
            System.out.println("===== 学生菜单 =====");
            System.out.println("1. 查看可选课程");
            System.out.println("2. 选择课程");
            System.out.println("3. 退选课程");
            System.out.println("4. 查看已选课程");
            System.out.println("5. 修改手机号");
            System.out.println("6. 退出");
            System.out.print("请选择操作（输入 1-6）：");

            int choice = scanner.nextInt();
            scanner.nextLine();


            switch (choice) {
                case 1:
                    // 查看可选课程
                    List<Course> availableCourses = studentService.getSelectedCourses(student.getStudentId());
                    System.out.println("可选课程：");
                    for (Course course : availableCourses) {
                        System.out.println(course);
                    }
                    break;
                case 2:
                    // 选择课程
                    System.out.print("请输入课程ID：");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    if (studentService.selectCourse(user.getId(), courseId)) {
                        System.out.println("选课成功！");
                    } else {
                        System.out.println("选课失败！");
                    }
                    break;
                case 3:
                    // 退选课程
                    System.out.print("请输入课程ID：");
                    courseId = scanner.nextInt();
                    scanner.nextLine();
                    if (studentService.dropCourse(user.getId(), courseId)) {
                        System.out.println("退课成功！");
                    } else {
                        System.out.println("退课失败！");
                    }
                    break;
                case 4:
                    // 查看已选课程
                    List<StudentCourse> selectedCourses = studentService.getSelectedCourses(user.getId());
                    System.out.println("已选课程：");
                    for (StudentCourse sc : selectedCourses) {
                        Course course = studentService.getCourseById(sc.getCourseId());
                        System.out.println(course);
                    }
                    break;
                case 5:
                    // 修改手机号
                    System.out.print("请输入新的手机号：");
                    String phone = scanner.nextLine();
                    Student student = studentService.getStudentByUserId(user.getId());
                    if (student != null && studentService.updateStudentPhone(student.getId(), phone)) {
                        System.out.println("手机号修改成功！");
                    } else {
                        System.out.println("手机号修改失败！");
                    }
                    break;
                case 6:
                    running = false;
                    break;
            }
        }
    }

    private static void adminMenu(Scanner scanner, User user) {
        AdminService adminService = new AdminService();
        boolean running = true;

        while (running) {
            System.out.println("===== 管理员菜单 =====");
            System.out.println("1. 查询所有学生");
            System.out.println("2. 修改学生手机号");
            System.out.println("3. 查询所有课程");
            System.out.println("4. 添加课程");
            System.out.println("5. 删除课程");
            System.out.println("6. 查询某课程的学生名单");
            System.out.println("7. 查询某学生的选课情况");
            System.out.println("8. 退出");
            System.out.print("请选择操作（输入 1-8）：");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // 查询所有学生
                    List<Student> students = adminService.getAllStudents();
                    System.out.println("所有学生：");
                    for (Student student : students) {
                        System.out.println(student);
                    }
                    break;
                case 2:
                    // 修改学生手机号
                    System.out.print("请输入学生ID：");
                    int studentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("请输入新的手机号：");
                    String phone = scanner.nextLine();
                    if (adminService.updateStudentPhone(studentId, phone)) {
                        System.out.println("手机号修改成功！");
                    } else {
                        System.out.println("手机号修改失败！");
                    }
                    break;
                case 3:
                    // 查询所有课程
                    List<Course> courses = adminService.getAllCourses();
                    System.out.println("所有课程：");
                    for (Course course : courses) {
                        System.out.println(course);
                    }
                    break;
                case 4:
                    // 添加课程
                    System.out.print("请输入课程名称：");
                    String name = scanner.nextLine();
                    System.out.print("请输入课程学分：");
                    int credits = scanner.nextInt();
                    scanner.nextLine();
                    if (adminService.addCourse(name, credits)) {
                        System.out.println("课程添加成功！");
                    } else {
                        System.out.println("课程添加失败！");
                    }
                    break;
                case 5:
                    // 删除课程
                    System.out.print("请输入课程ID：");
                    int courseId = scanner.nextInt();
                    scanner.nextLine();
                    if (adminService.deleteCourse(courseId)) {
                        System.out.println("课程删除成功！");
                    } else {
                        System.out.println("课程删除失败！");
                    }
                    break;
                case 6:
                    // 查询某课程的学生名单
                    System.out.print("请输入课程ID：");
                    courseId = scanner.nextInt();
                    scanner.nextLine();
                    List<StudentCourse> studentsByCourse = adminService.getStudentsByCourse(courseId);
                    System.out.println("选课学生：");
                    for (StudentCourse sc : studentsByCourse) {
                        Student student = adminService.getStudentById(sc.getStudentId());
                        System.out.println(student);
                    }
                    break;
                case 7:
                    // 查询某学生的选课情况
                    System.out.print("请输入学生ID：");
                    studentId = scanner.nextInt();
                    scanner.nextLine();
                    List<StudentCourse> coursesByStudent = adminService.getCoursesByStudent(studentId);
                    System.out.println("学生选课：");
                    for (StudentCourse sc : coursesByStudent) {
                        Course course = adminService.getCourseById(sc.getCourseId());
                        System.out.println(course);
                    }
                    break;
                case 8:
                    running = false;
                    break;
            }
        }
    }
}