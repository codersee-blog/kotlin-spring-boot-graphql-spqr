package com.codersee.graphqlspqr.mutation

import com.codersee.graphqlspqr.model.Employee
import com.codersee.graphqlspqr.model.EmployeeStatus
import com.codersee.graphqlspqr.repository.EmployeeRepository
import io.leangen.graphql.annotations.GraphQLMutation
import org.springframework.stereotype.Component

@Component
class EmployeeMutation(
    private val employeeRepository: EmployeeRepository
) {

    @GraphQLMutation(name = "newEmployee")
    fun newEmployee(
        firstName: String,
        lastName: String,
        status: EmployeeStatus,
        companyId: Long
    ): Employee =
        employeeRepository.create(firstName, lastName, status, companyId)

    @GraphQLMutation(name = "deleteEmployee")
    fun deleteEmployee(id: Long): Boolean =
        employeeRepository.delete(id)

}