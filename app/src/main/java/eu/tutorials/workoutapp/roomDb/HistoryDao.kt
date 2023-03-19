package eu.tutorials.workoutapp.roomDb

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface HistoryDao {
    @Insert
    fun insert(historyEntity: HistoryEntity)
}