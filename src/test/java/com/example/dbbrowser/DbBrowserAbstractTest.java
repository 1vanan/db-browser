package com.example.dbbrowser;

import com.example.dbbrowser.dto.ConnectionProperties;

/*
Test data for all test cases.
 */
public abstract class DbBrowserAbstractTest {

    protected static final String NAME1 = "instance_name";
    //change hostname in case if it doesn't match pattern from @BrowserController
    protected final String HOST_NAME1 = "localhost";
    protected final String PORT1 = "8080";
    protected final String DB_NAME1 = "db_name";
    protected static final String USERNAME1 = "John";
    protected static final String PASSWORD1 = "veryStrongpaSsWord123!";

    protected final String NAME2 = "another_instance_name";
    //change hostname in case if it doesn't match pattern from @BrowserController
    protected final String HOST_NAME2 = "localhost";
    protected final String PORT2 = "$8088";
    protected final String DB_NAME2 = "another_db_name";
    protected final String USERNAME2 = "Malkovich";
    protected final String PASSWORD2 = "notsostrong";

    protected ConnectionProperties properties1 =
        new ConnectionProperties(1L, NAME1, HOST_NAME1, PORT1, DB_NAME1, USERNAME1, PASSWORD1);

    protected ConnectionProperties properties2 =
        new ConnectionProperties(2L, NAME2, HOST_NAME2, PORT2, DB_NAME2, USERNAME2, PASSWORD2);

    protected ConnectionProperties propertiesFake =
        new ConnectionProperties(3L, NAME1 + NAME2, HOST_NAME1 + HOST_NAME2, PORT1 + PORT2,
            DB_NAME1 + DB_NAME2, USERNAME1 + USERNAME2, PASSWORD1 + PASSWORD2);
}
