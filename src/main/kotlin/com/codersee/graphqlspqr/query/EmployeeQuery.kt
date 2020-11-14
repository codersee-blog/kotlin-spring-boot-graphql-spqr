package com.codersee.graphqlspqr.query

import com.codersee.graphqlspqr.model.Employee
import com.codersee.graphqlspqr.repository.EmployeeRepository
import io.leangen.graphql.annotations.GraphQLQuery
import org.springframework.stereotype.Component

@Component
class EmployeeQuery(
    private val employeeRepository: EmployeeRepository
) {

    @GraphQLQuery(name = "employees")
    fun employees(): Set<Employee> =
        employeeRepository.findAll()

    @GraphQLQuery(name = "employeeById")
    fun employeeById(id: Long): Employee =
        employeeRepository.findById(id)
}