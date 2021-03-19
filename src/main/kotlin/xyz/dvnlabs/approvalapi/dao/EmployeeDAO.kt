package xyz.dvnlabs.approvalapi.dao

import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.Employee

@Repository
interface EmployeeDAO : GenericDAO<Employee, String> {
}