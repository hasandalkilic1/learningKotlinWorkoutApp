package eu.tutorials.workoutapp.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

        when{
            model.getIsSelected()->{
                holder.itemView.tvExerciseItem.background=ContextCompat.getDrawable(context,R.drawable.item_circular_thin_color_accent_border)
                holder.itemView.tvExerciseItem.setTextColor(Color.parseColor("#212121"))
            }
            model.getIsCompleted()->{
                holder.itemView.tvExerciseItem.background=ContextCompat.getDrawable(context,R.drawable.item_circular_color_accent_background)
                holder.itemView.tvExerciseItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else->{
                holder.itemView.tvExerciseItem.background=ContextCompat.getDrawable(context,R.drawable.item_circular_color_gray_background)
                holder.itemView.tvExerciseItem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}