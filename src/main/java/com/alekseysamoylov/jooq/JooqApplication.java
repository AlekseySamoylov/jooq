package com.alekseysamoylov.jooq;

import static com.alekseysamoylov.jooq.generated.db.Tables.USER;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alekseysamoylov.jooq.generated.db.tables.records.UserRecord;

@SpringBootApplication
public class JooqApplication implements CommandLineRunner {

    private final DSLContext dslContext;

    @Autowired
    public JooqApplication(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public static void main(String[] args) {
        SpringApplication.run(JooqApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserRecord user = dslContext.selectFrom(USER)
                .where(USER.FIRSTNAME.like("%Aleks%")).fetchSingle();

        System.out.println(user);
    }
}
