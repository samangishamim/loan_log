package repository.spouseRepository;

import base.repository.BaseRepository;
import model.Spouse;
import model.Student;

import java.util.Optional;

public interface SpouseRepository extends BaseRepository<Spouse, Long> {
    public Optional<Spouse> findSpouseStudent (Student student);

}
