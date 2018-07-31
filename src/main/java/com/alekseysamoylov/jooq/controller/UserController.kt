package com.alekseysamoylov.jooq.controller

import com.alekseysamoylov.jooq.entity.User
import com.alekseysamoylov.jooq.generated.db.Tables.USER
import org.jooq.DSLContext
import org.jooq.impl.DSL.count
import org.jooq.impl.DSL.row
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import javax.servlet.http.HttpServletResponse

@Controller
@RequestMapping("/api")
class UserController(private val dslContext: DSLContext) {

    @GetMapping("/user")
    @ResponseBody
    fun getUsers(): List<User> = dslContext.selectFrom(USER).fetchInto(User::class.java)

    @GetMapping("/user/{userId}")
    fun getUser(@PathVariable userId: Int, response: HttpServletResponse) {
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        dslContext.select(USER.ID, USER.FIRSTNAME, USER.LASTNAME).from(USER).where(USER.ID.eq(userId)).fetch()
                .formatJSON(response.outputStream)
    }

    @GetMapping("/count")
    fun getUserCount(response: HttpServletResponse) {
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        dslContext.select(count(USER.ID)).from(USER).fetch()
                .formatJSON(response.outputStream)
    }

    @GetMapping("/test/{newName}")
    @ResponseBody
    fun test(@PathVariable newName: String): String {
        dslContext.update(USER).set(row(USER.FIRSTNAME), row(newName))
                .where(USER.ID.eq(1)).execute()

        // creation and returning native SQL query
        return dslContext.update(USER).set(row(USER.FIRSTNAME), row(newName))
                .where(USER.ID.eq(1)).sql

    }

}
