package com.glaserproject.spacexploration.DbTests;

import com.glaserproject.spacexploration.CompanyInfoObjects.AboutSpaceX;
import com.glaserproject.spacexploration.CompanyInfoObjects.Milestone;
import com.glaserproject.spacexploration.LaunchObjects.Launch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;


/**
 * Test for DAO and Db - testing insert of objects AboutSpacex, Milestones and Launches
 * and testing returning LiveData from Db
 */


@RunWith(MockitoJUnitRunner.class)
public class SpacexDaoTest extends DbTest {


    @SuppressWarnings("FieldCanBeLocal")
    private final String MISSION_NAME_TEST_STRING = "mission name";
    @SuppressWarnings("FieldCanBeLocal")
    private final String CEO_NAME_TEST_STRING = "Elon Musk";
    @SuppressWarnings("FieldCanBeLocal")
    private final String TITLE_TEST_STRING = "title";


    @Mock
    private AboutSpaceX mockAboutSpacex;
    @Mock
    private Milestone mockMilestone;
    @Mock
    private Launch mockLaunch;


    @Test
    public void insertAndLoadAboutSpacexTest() throws InterruptedException {

        when(mockAboutSpacex.getCeo()).thenReturn(CEO_NAME_TEST_STRING);

        database.spacexDao().insertAboutSpaceX(mockAboutSpacex);

        final AboutSpaceX aboutSpaceXLoaded = LiveDataTest.getValue(database.spacexDao().getAboutSpaceXLiveData());
        assertThat(aboutSpaceXLoaded.getCeo(), is(CEO_NAME_TEST_STRING));
    }


    @Test
    public void insertAndLoadMilestonesTest() throws InterruptedException {

        //setup List
        List<Milestone> milestonesList = new ArrayList<>();
        milestonesList.add(mockMilestone);


        when(mockMilestone.getTitle()).thenReturn(TITLE_TEST_STRING);


        database.spacexDao().insertMilestones(milestonesList);


        Milestone returnMilestone = LiveDataTest.getListValue(database.spacexDao().getAllMilestones()).get(0);

        assertThat(returnMilestone.getTitle(), is(TITLE_TEST_STRING));
    }


    @Test
    public void insertAndLoadLaunchesTest() throws InterruptedException {

        //setup List
        List<Launch> launchesList = new ArrayList<>();
        launchesList.add(mockLaunch);


        when(mockLaunch.getMission_name()).thenReturn(MISSION_NAME_TEST_STRING);
        database.spacexDao().insertLaunches(launchesList);


        Launch returnLaunch = LiveDataTest.getListValue(database.spacexDao().getLaunches()).get(0);
        assertThat(returnLaunch.getMission_name(), is(MISSION_NAME_TEST_STRING));
    }


}
