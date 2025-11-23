package com;

import java.io.File;

public class postgresqlSwitcher {
    public postgresqlSwitcher() {
		super();
	}

    public static void main(String[] args) {
        System.out.println("Switcher started");

        File current     = new File("./src/main/resources/application.properties");
        File postgres    = new File("./src/main/resources/application-postgres.properties");
        File nonPostgres = new File("./src/main/resources/application-not-postgres.properties");

        if (!current.exists()) {
            System.out.println("ERROR: Current settings are missing, expected at ./src/main/resources/application.properties");
        } else if (!(postgres.exists() || nonPostgres.exists())) {
            System.out.println("ERROR: The settings to replace with are missing, this is either a file at ./src/main/resources/application-postgres.properties or at ./src/main/resources/application-not-postgres.properties.");
        } else if (postgres.exists() && nonPostgres.exists()) {
            System.out.println("ERROR: Both settings files exist, delete either ./src/main/resources/application-postgres.properties or ./src/main/resources/application-not-postgres.properties.");
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
