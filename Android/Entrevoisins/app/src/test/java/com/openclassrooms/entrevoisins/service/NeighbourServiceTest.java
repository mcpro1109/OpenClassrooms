package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    Neighbour addneighbour;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    //test ajout d'un voisin
    @Test
    public void createNeighbour() {
        List<Neighbour> neighbours = service.getNeighbours();
        service.createNeighbour(addneighbour);
        assertTrue(neighbours.contains(addneighbour));
    }


    //test ajout aux favoris
    @Test
    public void getNeighbourFavorite() {
        List<Neighbour> neighbours = service.getFavoriteNeighbours();
        List<Neighbour> favoriteNeighbour = new ArrayList<>();
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(favoriteNeighbour.toArray()));
    }


    //test ajout et retrait d'un favoris
    @Test
    public void addRemoveFavoriteNeighbour() {
        List<Neighbour> favoriteNeighbour = service.getFavoriteNeighbours();
        service.setFavoris(addneighbour, true);
        assertTrue(favoriteNeighbour.contains(addneighbour));

        service.setFavoris(addneighbour, false);
        assertFalse(favoriteNeighbour.contains(addneighbour));
    }
}