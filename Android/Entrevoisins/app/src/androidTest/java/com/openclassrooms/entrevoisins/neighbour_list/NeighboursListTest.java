
package com.openclassrooms.entrevoisins.neighbour_list;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;
    int ItemCount = 12;
    int positionItem = 0;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());

    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
    }

    //test si accès au profil
    @Test
    public void viewProfil() {
        //check si liste existe
        onView(withId(R.id.list_neighbours)).check(withItemCount(ItemCount));
        //clic sur un item de la liste
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        //check si la vue profil s'affiche
        onView(withId(R.id.profil)).check(matches(isDisplayed()));

    }

    //test vérifier si le textview du nom de l'utilisateur est rempli
    @Test
    public void myNeighboursProfil_shouldNotBeEmpty() {
        //check si liste existe
        onView(withId(R.id.list_neighbours)).check(withItemCount(ItemCount));
        // Click on item in position 2
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        // Verifying if TextView is correct
        onView(withId(R.id.nameProfilView)).check(matches(withText("Chloé")));

    }


    //test sur retrait de l'utilisateur de la liste des favoris
    @Test
    public void removeNeighbourFavoriteList() {
        //test si ajout dans les favoris en cliquant sur le floatingactionbutton
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(positionItem, click()));
        onView(withId(R.id.favorisView)).perform(click());
        //retour
        pressBack();
        //switch vers onglet favoris
        onView(withId(R.id.container)).perform(swipeLeft());
        //check si le nombre de favoris correspond au nombre de la liste
        onView(ViewMatchers.withId(R.id.list_favoris_neighbours)).check(withItemCount(1));

        //retrait des favoris
        onView(ViewMatchers.withId(R.id.list_favoris_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(positionItem, new DeleteViewAction()));
        onView(ViewMatchers.withId(R.id.list_favoris_neighbours)).check(withItemCount(1));

    }

}