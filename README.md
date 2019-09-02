

## Initiatives

In this project, online appointment system has been designed and implemented. Here are the design initiatives.

 * One day consist of Slots, number of slots in a day can be dynamicaly changed. For that purpose, SLOT_DEFINITION table has  been created.
 * By a scheduled job, all required time slots are created by the application which means that databases stores all time slots as calendar internally. This brings simplification to code complexity and performance incresement. For sure there might be some disadvanteges(srotage capacity etc.). Please check the SLOT table for further details.
 * By the same scheduled job, for each stylist, new database records are created for each slot mentioned above. This part is open for discussion. Please check STYLIST_SLOT table for details. This table is the most critical part of the system and Optimictic Lock is used to prevent system to provide consistent data.
 * DataRange is set harcodedly 14 days which means that cleint can check up to 14 days upcoming days. Otherwise there will be no SLOT created by the scheduled job.
 * Stylist can work from 9 am to 16 pm and this values are also hardcoded. Also a stylist's state must be ACTIVE in order to accept appointments.


## Technical Details
 * Spring Boot application with Java 11.
 * Maven is the build tool.
 * H2 is the database.
 * ORM is used to database management but there are some native queries as well.
 * Swagger support is added.
 * Both unit and integration tests are implemented.


## Missing items.
 * No database indexes are created. Initial concern was not the performance as currenctly there is no data available.
 * Test coverage is definitely not enough, quite critical cases were not tested bacause of extendibility of the project and time restriction.
 * There is no mechanism to increase distribution of appoitments equally base on Stylist. I was thinking about ranking Stylist by day, by week or something similar. For short term solution, a group by query can be used in order to pick Stylist who has least appointment. It is a critical topic to study on.
 * No Circuit Breaker is used.
 * Rest api ExceptionHandler can ben enhanced with Custom exceptions.
 * DDD can be another approach as it requires many domain knowledge.
 * No spring profile exists at the present time.

