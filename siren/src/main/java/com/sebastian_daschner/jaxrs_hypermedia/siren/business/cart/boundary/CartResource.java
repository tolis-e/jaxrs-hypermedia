package com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.boundary;

import com.google.code.siren4j.component.Entity;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.BookSelection;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.Selection;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.cart.entity.ShoppingCart;
import com.sebastian_daschner.jaxrs_hypermedia.siren.business.orders.boundary.EntityBuilder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Stateless
@Produces(MediaType.APPLICATION_JSON)
@Path("shopping_cart")
public class CartResource {

    @Inject
    MockShoppingCart shoppingCart;

    @Inject
    EntityBuilder entityBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public Entity getShoppingCart() {
        final ShoppingCart cart = shoppingCart.getShoppingCart();
        return entityBuilder.buildShoppingCart(cart, uriInfo);
    }

    @POST
    public void addItem(@Valid @NotNull BookSelection selection) {
        shoppingCart.addBookSelection(selection);
    }

    @PUT
    @Path("{id}")
    public void updateSelection(@PathParam("id") long selectionId, @Valid @NotNull Selection selectionUpdate) {
        shoppingCart.updateBookSelection(selectionId, selectionUpdate.getQuantity());
    }

    @DELETE
    @Path("{id}")
    public void deleteSelection(@PathParam("id") long selectionId) {
        shoppingCart.updateBookSelection(selectionId, 0);
    }

}
