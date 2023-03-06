package eu.tutorials.workoutapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.tutorials.workoutapp.R
import eu.tutorials.workoutapp.models.ExerciseModel
import kotlinx.android.synthetic.main.item_exercise.view.*

class ExerciseAdapter(val context:Context,val list:ArrayList<ExerciseModel>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(
            R.layout.item_exercise,
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model:ExerciseModel=list[position]

        holder.itemView.tvExerciseItem.text=model.getID().toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }
}