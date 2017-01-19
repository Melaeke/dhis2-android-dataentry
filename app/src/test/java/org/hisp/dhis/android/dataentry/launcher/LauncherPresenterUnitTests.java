package org.hisp.dhis.android.dataentry.launcher;

import org.hisp.dhis.android.core.configuration.ConfigurationModel;
import org.hisp.dhis.android.dataentry.server.ConfigurationRepository;
import org.hisp.dhis.android.dataentry.utils.MockSchedulersProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LauncherPresenterUnitTests {

    @Mock
    private LauncherView launcherView;

    @Mock
    private ConfigurationRepository configurationRepository;

    private ConfigurationModel configuration;
    private LauncherPresenter launcherPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        configuration = mock(ConfigurationModel.class);
        when(configuration.serverUrl()).thenReturn("test_server_url");

        launcherPresenter = new LauncherPresenterImpl(new MockSchedulersProvider(), configurationRepository);
        launcherPresenter.onAttach(launcherView);
    }

    @Test
    public void isUserLoggedIn_shouldCallNavigateToLoginView_ifConfigurationIsNotPresent() {
        // server is not configured
        when(configuration.serverUrl()).thenReturn("");

        when(configurationRepository.isUserLoggedIn()).thenReturn(Observable.just(false));

        launcherPresenter.isUserLoggedIn();

        verify(launcherView).navigateToLoginView();
    }

    @Test
    public void isUserLoggedIn_shouldCallNavigateToLoginView_ifUserNotPresent() {
        when(configurationRepository.isUserLoggedIn()).thenReturn(Observable.just(false));

        launcherPresenter.isUserLoggedIn();

        verify(launcherView).navigateToLoginView();
    }

    @Test
    public void isUserLoggedIn_shouldCallNavigateToHomeView_ifUserPresent() {
        when(configurationRepository.isUserLoggedIn()).thenReturn(Observable.just(true));

        launcherPresenter.isUserLoggedIn();

        verify(launcherView).navigateToHomeView();
    }

    @Test
    public void isUserLoggedIn_shouldCallNavigationToHomeView_ifUserRepositoryIsNull() {
        LauncherPresenter launcherPresenter = new LauncherPresenterImpl(
                new MockSchedulersProvider(), null);
        launcherPresenter.onAttach(launcherView);

        launcherPresenter.isUserLoggedIn();

        verify(launcherView).navigateToLoginView();
    }

    @Test
    public void onAttach_shouldNotInvokeView() {
        launcherPresenter.onAttach(launcherView);

        verify(launcherView, never()).navigateToLoginView();
        verify(launcherView, never()).navigateToHomeView();
    }

    @Test
    public void viewMethods_shouldNotBeCalled_afterDetach() {
        when(configurationRepository.isUserLoggedIn()).thenReturn(Observable.just(true));

        launcherPresenter.onDetach();
        launcherPresenter.isUserLoggedIn();

        verify(launcherView, never()).navigateToLoginView();
        verify(launcherView, never()).navigateToHomeView();
    }
}