package com.github.jfsql.demo;

import java.io.File;
import java.nio.file.Path;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final Path RESOURCES_PATH = Path.of(
        System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator
            + "resources");
    public static final String JFSQL_CONNECTION_STRING =
        "jdbc:jfsql:" + RESOURCES_PATH + File.separator + "databases" + File.separator + "myDatabase";

}
