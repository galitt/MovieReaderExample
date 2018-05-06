package com.galit.moviereaderexample.data.local;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by galit on 03/05/2018.
 */

public class MovieReaderRealmMigration implements RealmMigration {

    public static final int SCHEMA_VERSION = 0;
    public static final String SCHEMA_NAME = "MovieReaderSchema";

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        //no migration for current version
    }
}
