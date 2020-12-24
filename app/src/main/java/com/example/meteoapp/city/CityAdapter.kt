package com.example.meteoapp.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoapp.R

class CityAdapter(private val cities: List<City>,
    private val cityListener: CityAdapter.CityItemListener): RecyclerView.Adapter<CityAdapter.ViewHolder>(),
    View.OnClickListener {

    interface CityItemListener {
        fun onCitySelected(city: City)
        fun onCityDeleted(city: City)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.cardview)!!
        //val iconView = itemView.findViewById<ImageView>(R.id.icon)
        val cityNameView = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city, parent, false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val city = cities[position]
        with(holder){
            cardView.tag = city
            cardView.setOnClickListener(this@CityAdapter)
            cityNameView.text = city.name
        }
    }

    override fun getItemCount(): Int = cities.size

    override fun onClick(view: View) {

        when(view.id){
            R.id.cardview -> cityListener.onCityDeleted(view.tag as City)
        }

    }
}