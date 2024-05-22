package service.spouseService;

import base.service.BaseService;
import model.Spouse;
import model.Student;

import java.util.Optional;

public interface SpouseService extends BaseService<Spouse, Long> {
    public Optional<Spouse> findSpouseOfStudent (Student student);
}
