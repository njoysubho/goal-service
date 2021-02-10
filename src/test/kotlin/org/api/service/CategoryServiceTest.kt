package org.api.service


import io.mockk.every
import io.mockk.mockk
import org.api.domain.Category
import org.api.port.ICategoryDAO
import org.junit.jupiter.api.BeforeEach
import java.util.*

class CategoryServiceTest {
    var categoryDAO = mockk<ICategoryDAO>()
    var categoryService = CategoryService(categoryDAO)
    val TEST_CATEGORY = Category(name = "cat1")

    @BeforeEach
    fun setup() {
        TEST_CATEGORY.id = UUID.randomUUID()
        every { categoryDAO.save(any()) }.returns(TEST_CATEGORY)
    }
}