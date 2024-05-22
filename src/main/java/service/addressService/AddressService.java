package service.addressService;

import base.service.BaseService;
import model.Address;
import model.Student;

import java.util.Optional;

public interface AddressService extends BaseService<Address,Long> {

    public Optional<Address> getStudentAddress(Student student);
}
