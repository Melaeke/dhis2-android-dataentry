package org.hisp.dhis.android.dataentry.reports.search;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class SearchArguments {

    @NonNull
    public abstract String entityUid();

    @NonNull
    public abstract String entityName();

    @NonNull
    public static SearchArguments create(@NonNull String uid, @NonNull String name) {
        return new AutoValue_SearchArguments(uid, name);
    }
}
