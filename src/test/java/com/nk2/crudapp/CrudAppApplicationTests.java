package com.nk2.crudapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrudAppApplicationTests {

    @Test
    void contextLoads() {
    }
    /*
    Tests
        Application creates database and tables exist
        Employee
            Create
                success
                failure - missing attributes
                success - when no departments passed
                failure -  department does not exist
            Retrieve by id
                success
                Exception check for employee not exist
            Update
                success
                failure - employee id does not exist
                failure - department id does not exist
            Delete
                success
                failure - employee id does not exist


        Department
            Create
                success
                failure - missing attributes
            Retrieve
                success
                failure - department id does not exist
            Update
                success -
                failure - readonly department update
                failure - id does not exist
            Delete
                success
                failure - id does not exist
                failure - readonly

     */


}
