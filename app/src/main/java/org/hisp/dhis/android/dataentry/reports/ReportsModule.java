package org.hisp.dhis.android.dataentry.reports;

import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;

import org.hisp.dhis.android.dataentry.commons.PerActivity;
import org.hisp.dhis.android.dataentry.utils.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@PerActivity
@Module
public final class ReportsModule {
    private final String programUid;

    public ReportsModule(@NonNull String programUid) {
        this.programUid = programUid;
    }

    @PerActivity
    @Provides
    ReportsRepository reportsRepository(BriteDatabase briteDatabase) {
        return new ReportsRepositoryImpl(briteDatabase, programUid);
    }

    @PerActivity
    @Provides
    ReportsPresenter reportsPresenter(ReportsRepository reportsRepository,
            SchedulerProvider schedulerProvider) {
        return new ReportsPresenterImpl(schedulerProvider, reportsRepository);
    }
}