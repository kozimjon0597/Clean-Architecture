package uz.kozimjon.cleanarchitecture.paper_db

import io.paperdb.Paper
import uz.kozimjon.cleanarchitecture.presentation.model.Cart
import uz.kozimjon.cleanarchitecture.presentation.model.Favourite

object Carts {
    private const val CARTS_DB_NAME = "carts"

    fun existCart(data: Cart): Boolean {
        val favourites = Paper.book().read<ArrayList<Cart>>(CARTS_DB_NAME)
        var isExist = false
        if (!favourites.isNullOrEmpty()) {
            for (favourite in favourites) {
                if (favourite.id == data.id) {
                    isExist = true
                    break
                }
            }
        }
        return isExist
    }

    fun addCart(data: Cart) {
        val favourites = Paper.book().read<ArrayList<Cart>>(CARTS_DB_NAME)
        if (favourites.isNullOrEmpty()) {
            val favourite = ArrayList<Cart>()
            favourite.add(data)
            Paper.book().write(CARTS_DB_NAME, favourite)
        } else {
            favourites.add(data)
            Paper.book().write(CARTS_DB_NAME, favourites)
        }
    }

    fun updateCart(cart: Cart) {
        val favourites = Paper.book().read<ArrayList<Cart>>(CARTS_DB_NAME)

        for (index in 0 until favourites!!.size) {
            if (favourites[index].id == cart.id) {
                favourites[index] = cart
                Paper.book().write(CARTS_DB_NAME, favourites)
                break
            }
        }
    }

    fun removeCart(data: Cart) {
        val favourites = Paper.book().read<ArrayList<Cart>>(CARTS_DB_NAME)
        favourites?.remove(data)
        Paper.book().write(CARTS_DB_NAME, favourites!!)
    }

    fun getCarts(): ArrayList<Cart> {
        return Paper.book().read(CARTS_DB_NAME, arrayListOf())!!
    }
}