package org.hisp.dhis.android.dataentry.commons.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.List;

@AutoValue
public abstract class Result<T> {

    @NonNull
    public abstract List<T> items();

    @Nullable
    public abstract Exception error();

    @NonNull
    public static <E> Result<E> success(@NonNull List<E> items) {
        return new AutoValue_Result<>(items, null);
    }

    @NonNull
    public static Result failure(@NonNull Exception exception) {
        return new AutoValue_Result<>(new ArrayList<>(), exception);
    }
}
