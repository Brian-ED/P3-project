package com;

import java.io.File;

public class postgresqlSwitcher {

    public static void main(String[] args) {
        System.out.println("Switcher started");

        File current     = new File("./src/main/resources/application.properties");
        File postgres    = new File("./src/main/resources/application-postgres.properties");
        File nonPostgres = new File("./src/main/resources/application-not-postgres.properties");

        if (!current.exists()) {
            System.out.println("ERROR: Current settings are missing, expected at "+current.getPath());
        } else if (!(postgres.exists() || nonPostgres.exists())) {
            System.out.println("ERROR: The settings to replace with are missing, this should be at either "+postgres.getPath()+" or at "+nonPostgres.getPath()+".");
        } else if (postgres.exists() && nonPostgres.exists()) {
            System.out.println("ERROR: Both settings files exist, delete either "+postgres.getPath()+" or "+nonPostgres.getPath()+".");
        } else if (postgres.exists()) {
            current.renameTo(nonPostgres);
            postgres.renameTo(current);
            System.out.println("Postgres enabled.");
        } else if (nonPostgres.exists()) {
            current.renameTo(postgres);
            nonPostgres.renameTo(current);
            System.out.println("Postgres disabled.");
        }
    }
}
