package uz.kozimjon.cleanarchitecture.paper_db

import io.paperdb.Paper
import uz.kozimjon.cleanarchitecture.presentation.model.Favourite

object Favourites {
    private const val FAVOURITES_DB_NAME = "favourites"

    fun existFavourite(data: Favourite): Boolean {
        val favourites = Paper.book().read<ArrayList<Favourite>>(FAVOURITES_DB_NAME)
        var isExist = false
        if (!favourites.isNullOrEmpty()) {
            for (favourite in favourites) {
                if (favourite == data) {
                    isExist = true
                    break
                }
            }
        }
        return isExist
    }

    fun addFavourite(data: Favourite) {
        val favourites = Paper.book().read<ArrayList<Favourite>>(FAVOURITES_DB_NAME)
        if (favourites.isNullOrEmpty()) {
            val favourite = ArrayList<Favourite>()
            favourite.add(data)
            Paper.book().write(FAVOURITES_DB_NAME, favourite)
        } else {
            favourites.add(data)
            Paper.book().write(FAVOURITES_DB_NAME, favourites)
        }
    }

    fun removeFavourite(data: Favourite) {
        val favourites = Paper.book().read<ArrayList<Favourite>>(FAVOURITES_DB_NAME)
        favourites?.remove(data)
        Paper.book().write(FAVOURITES_DB_NAME, favourites!!)
    }

    fun getFavourites(): ArrayList<Favourite> {
        return Paper.book().read(FAVOURITES_DB_NAME, arrayListOf())!!
    }
}