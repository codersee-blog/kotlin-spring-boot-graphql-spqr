package com.codersee.graphqlspqr.query

import com.codersee.graphqlspqr.model.Company
import com.codersee.graphqlspqr.repository.CompanyRepository
import io.leangen.graphql.annotations.GraphQLQuery
import org.springframework.stereotype.Component

@Component
class CompanyQuery(
    private val companyRepository: CompanyRepository
) {

    @GraphQLQuery(name = "companies")
    fun companies(): Set<Company> =
        companyRepository.findAll()

    @GraphQLQuery(name = "companyById")
    fun companyById(id: Long): Company =
        companyRepository.findById(id)
}

