package com.example.myrecycledemo

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : AppCompatActivity() {
    val rcv by lazy { findViewById<RecyclerView>(R.id.rcv)}
    val onClickAction = fun (weekday: Any){
        val index =weekList.indexOfFirst { it.weekday == weekday }
        if(index !=-1){
            val item = weekList[index]
            weekList[index]= item.copy(selected = true)
            rcv.adapter?.notifyItemChanged(index)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rcv.adapter= WeekAdapters(weekList, onClickAction)
        rcv.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


    }
}

class WeekAdapters(val list: List<Weekdays>, val onClickAction : (Any)-> Unit ) : Adapter<WeekAdapters.WeekDaysViewHolder>() {

    inner class WeekDaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val weekday = itemView.findViewById<TextView>(R.id.tv_weekday)
        val day_of_month = itemView.findViewById<TextView>(R.id.tv_day_of_month)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDaysViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val itemView= inflator.inflate(R.layout.card_item,parent,false)
        return WeekDaysViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WeekDaysViewHolder, position: Int) {
        val d= list[position]
        holder.weekday.text= d.weekday
        holder.day_of_month.text= d.dayOfMonth.toString()
        holder.itemView.setOnClickListener{
            onClickAction(d.weekday)
            if(d.selected){
                holder.itemView.background= ResourcesCompat.getDrawable(
                    holder.itemView.context.resources,
                    R.drawable.items_background,
                    null)

            }
            else{
                holder.itemView.background= ResourcesCompat.getDrawable(
                    holder.itemView.context.resources,
                    R.drawable.item_not_selected_bg,
                    null)
            }
        }


    }

}

data class Weekdays(
    val weekday : String,
    val dayOfMonth : Int,
    val selected : Boolean = false
)

val weekList = mutableListOf<Weekdays>(
    Weekdays("Monday",1),
    Weekdays("Tuesday",2),
    Weekdays("Wednesday",3),
    Weekdays("Thursday",4),
    Weekdays("Friday",5),
    Weekdays("Saturday",6),
    Weekdays("Sunday",7)
)