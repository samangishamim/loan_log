package repository.addressRepository;

import base.repository.BaseRepository;
import model.Address;
import model.Student;

import java.util.Optional;

public interface AddressRepository extends BaseRepository<Address, Long> {
    public Optional<Address> getStudentAddress(Student student);
}
