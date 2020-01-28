package com.taxtelecom.arinamurasheva.addressbook.Contact;

import com.taxtelecom.arinamurasheva.addressbook.Model.Department;
import com.taxtelecom.arinamurasheva.addressbook.Model.Person;

import java.util.List;

public class ContactInflater {

    public static Person getPerson(Department dept, int position) {
        List<Person> persons = dept.getEmployees();
        return persons.get(position - 1);
    }

    public static  Department getDepartment(Department dept, int position) {
        List<Department> innerDepts = dept.getDepartments();
        return innerDepts.get(position - 1);
    }


}
