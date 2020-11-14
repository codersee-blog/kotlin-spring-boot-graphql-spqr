package com.codersee.graphqlspqr.mutation

import com.codersee.graphqlspqr.model.Company
import com.codersee.graphqlspqr.repository.CompanyRepository
import io.leangen.graphql.annotations.GraphQLMutation
import org.springframework.stereotype.Component

@Component
class CompanyMutation(
    private val companyRepository: CompanyRepository
) {

    @GraphQLMutation(name = "newCompany")
    fun newCompany(name: String, address: String, zipCode: String): Company =
        companyRepository.create(name, address, zipCode)

    @GraphQLMutation(name = "deleteCompany")
    fun deleteCompany(id: Long): Boolean =
        companyRepository.delete(id)
}