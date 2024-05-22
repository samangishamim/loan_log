package service.studentService;

import base.service.BaseService;
import model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService extends BaseService<Student, Long> {
    List<Student> studentSignIn(String nationalId, String password);
     List<Student> studentInfo(String nationalId);
}
