package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Event fired when a user deletes a Neighbour
 */
public class FavoriteNeighbourEvent {

    public Neighbour neighbour;
    public boolean isFavorite;

    /**
     * Constructor.
     *
     * @param neighbour
     */
    public FavoriteNeighbourEvent(Neighbour neighbour, boolean isFavorite) {

        this.neighbour = neighbour;
        this.isFavorite = isFavorite;


    }


}
